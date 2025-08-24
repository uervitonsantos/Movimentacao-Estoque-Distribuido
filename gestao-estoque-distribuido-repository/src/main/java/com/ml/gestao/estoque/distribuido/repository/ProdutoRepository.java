package com.ml.gestao.estoque.distribuido.repository;

import com.google.common.collect.Lists;
import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.canonicoFactory.ProdutoCanonicoFactory;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.filtros.ProdutoFiltro;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository extends GestaoEstoqueDistribuidoRepositorio {

    @Autowired
    private ProdutoCanonicoFactory produtoCanonicoFactory;

    public Produto busca(Long produtoID) {
        return busca(Produto.class, produtoID);
    }

    public Produto buscaSku(String sku) {
        return busca(Produto.class, sku);
    }

    public ProdutoCanonico buscaProduto(Long produtoID) {
        Produto produto = busca(produtoID);
        return Optional.ofNullable(produto).map(e -> {
            return produtoCanonicoFactory.builderProduto(e);
        }).orElse(null);
    }

    public List<ProdutoCanonico> buscaProdutos(FiltroWrapper filtro) {
        ProdutoFiltro produtoFiltro = (ProdutoFiltro) filtro.getFiltro();
        if (filtro.hasPaginacao()) {
            Long qtdRegistros = countRegistros(produtoFiltro);
            if (qtdRegistros == 0) {
                return Lists.newArrayList();
            }
            filtro.getPaginacao().setTotalRegistros(qtdRegistros);
            CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
            Root<Produto> root = criteria.from(Produto.class);
            criteria.distinct(true).select(root.get("produtoID"))
                    .where(aplicaFiltros(root, produtoFiltro, false)).orderBy(asc(root.get("produtoID")));
            List<Long> resultado = paginarResultado(criteria, filtro);
            if (resultado.isEmpty()) {
                return Lists.newArrayList();
            }
            produtoFiltro.setProdutoID(resultado);
        }
        return buscaProdutos(produtoFiltro);
    }

    private List<ProdutoCanonico> buscaProdutos(ProdutoFiltro produtoFiltro) {
        CriteriaQuery<Produto> criteria = criteriaQuery(Produto.class);
        Root<Produto> root = criteria.from(Produto.class);
        criteria.distinct(true).select(root).where(aplicaFiltros(root, produtoFiltro, true))
                .orderBy(asc(root.get("produtoID")));
        TypedQuery<Produto> query = typedQuery(criteria);
        return produtoCanonicoFactory.produtosCanonico(query.getResultList());
    }

    private Long countRegistros(ProdutoFiltro produtoFiltro) {
        CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
        Root<Produto> root = criteria.from(Produto.class);
        criteria.select(criteriaBuilder().countDistinct(root.get("produtoID")))
                .where(aplicaFiltros(root, produtoFiltro, false));
        return typedQuery(criteria).getSingleResult();
    }

    private Predicate[] aplicaFiltros(Root<Produto> root, ProdutoFiltro produtoFiltro, boolean fetch) {
        CriteriaBuilder builder = criteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        if (produtoFiltro.hasProdutoID()) {
            predicates.add(root.get("produtoID").in(produtoFiltro.getProdutoID()));
        }
        if (produtoFiltro.hasNomeProduto()) {
            predicates.add(builder.like(builder.upper(root.get("nomeProduto")),
                    "%" + produtoFiltro.getNomeProduto().toUpperCase() + "%"));
        }
        if (produtoFiltro.hasCategoria()) {
            predicates.add(builder.equal(builder.upper(root.get("categoria")),
                    produtoFiltro.getCategoria().toUpperCase()));
        }
        if (produtoFiltro.hasPrecoMin()) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("preco"), produtoFiltro.getPrecoMin()));
        }
        if (produtoFiltro.hasPrecoMax()) {
            predicates.add(builder.lessThanOrEqualTo(root.get("preco"), produtoFiltro.getPrecoMax()));
        }

        return predicates.toArray(new Predicate[0]);
    }

    public Produto salvaProduto(Produto produto) {
        return merge(produto);
    }
}
