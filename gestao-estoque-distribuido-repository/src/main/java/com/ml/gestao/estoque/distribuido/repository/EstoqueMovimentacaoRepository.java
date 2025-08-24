package com.ml.gestao.estoque.distribuido.repository;

import com.google.common.collect.Lists;
import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.canonicoFactory.EstoqueMovimentoCanonicoFactory;
import com.ml.gestao.estoque.distribuido.entidade.EstoqueMovimento;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.filtros.EstoqueMovimentoFiltro;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EstoqueMovimentacaoRepository extends GestaoEstoqueDistribuidoRepositorio {

    @Autowired
    private EstoqueMovimentoCanonicoFactory estoqueMovimentoCanonicoFactory;

    public EstoqueMovimento busca(Long estoqueMovimentoID) {
        return busca(EstoqueMovimento.class, estoqueMovimentoID);
    }

    public EstoqueMovimentoCanonico buscaEstoqueMovimento(Long estoqueMovimentoID) {
        EstoqueMovimento estoqueMovimento = busca(estoqueMovimentoID);
        return Optional.ofNullable(estoqueMovimento).map(e ->
                estoqueMovimentoCanonicoFactory.builderMovimento(e)).orElse(null);
    }

    public List<EstoqueMovimentoCanonico> buscaEstoqueMovimentos(FiltroWrapper filtro) {
        EstoqueMovimentoFiltro estoqueMovimentoFiltro = (EstoqueMovimentoFiltro) filtro.getFiltro();
        if (filtro.hasPaginacao()) {
            Long qtdRegistros = countRegistros(estoqueMovimentoFiltro);
            if (qtdRegistros == 0) {
                return Lists.newArrayList();
            }
            filtro.getPaginacao().setTotalRegistros(qtdRegistros);
            CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
            Root<EstoqueMovimento> root = criteria.from(EstoqueMovimento.class);
            criteria.distinct(true).select(root.get("movimentoID"))
                    .where(aplicaFiltros(root, estoqueMovimentoFiltro, false))
                    .orderBy(asc(root.get("movimentoID")));
            List<Long> resultado = paginarResultado(criteria, filtro);
            if (resultado.isEmpty()) {
                return Lists.newArrayList();
            }
            estoqueMovimentoFiltro.setMovimentoID(resultado);
        }
        return buscaEstoqueMovimentos(estoqueMovimentoFiltro);
    }

    private List<EstoqueMovimentoCanonico> buscaEstoqueMovimentos(EstoqueMovimentoFiltro estoqueMovimentoFiltro) {
        CriteriaQuery<EstoqueMovimento> criteria = criteriaQuery(EstoqueMovimento.class);
        Root<EstoqueMovimento> root = criteria.from(EstoqueMovimento.class);
        criteria.distinct(true).select(root).where(aplicaFiltros(root, estoqueMovimentoFiltro, true))
                .orderBy(asc(root.get("movimentoID")));
        TypedQuery<EstoqueMovimento> query = typedQuery(criteria);
        return estoqueMovimentoCanonicoFactory.movimentosCanonico(query.getResultList());
    }

    private Long countRegistros(EstoqueMovimentoFiltro estoqueMovimentoFiltro) {
        CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
        Root<EstoqueMovimento> root = criteria.from(EstoqueMovimento.class);
        criteria.select(criteriaBuilder().countDistinct(root.get("movimentoID")))
                .where(aplicaFiltros(root, estoqueMovimentoFiltro, false));
        return typedQuery(criteria).getSingleResult();
    }

    private Predicate[] aplicaFiltros(Root<EstoqueMovimento> root, EstoqueMovimentoFiltro estoqueFiltro, boolean fetch) {
        CriteriaBuilder builder = criteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        Join<EstoqueMovimento, Produto> joinProdutos = root.join("produto", JoinType.LEFT);
        Join<EstoqueMovimento, Loja> joinLojas = root.join("loja", JoinType.LEFT);

        if (estoqueFiltro.hasProdutoID()) {
            predicates.add(builder.equal(joinProdutos.get("produtoID"), estoqueFiltro.getProdutoID()));
        }
        if (estoqueFiltro.hasLojaID()) {
            predicates.add(builder.equal(joinLojas.get("lojaID"), estoqueFiltro.getLojaID()));
        }
        if (estoqueFiltro.hasQuantidadeMin()) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("quantidade"), estoqueFiltro.getQuantidadeMin()));
        }
        if (estoqueFiltro.hasQuantidadeMax()) {
            predicates.add(builder.lessThanOrEqualTo(root.get("quantidade"), estoqueFiltro.getQuantidadeMax()));
        }

        return predicates.toArray(new Predicate[0]);
    }

    public EstoqueMovimento salvaEstoqueMovimento(EstoqueMovimento estoqueMovimento) {
        return merge(estoqueMovimento);
    }
}
