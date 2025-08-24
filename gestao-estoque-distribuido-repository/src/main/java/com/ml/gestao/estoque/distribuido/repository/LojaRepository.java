package com.ml.gestao.estoque.distribuido.repository;

import com.google.common.collect.Lists;
import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.canonicoFactory.LojaCanonicoFactory;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.filtros.LojaFiltro;
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
public class LojaRepository extends GestaoEstoqueDistribuidoRepositorio {

    @Autowired
    private LojaCanonicoFactory lojaCanonicoFactory;

    public Loja busca(Long lojaID) {
        return busca(Loja.class, lojaID);
    }

    public LojaCanonico buscaLoja(Long lojaID) {
        Loja loja = busca(lojaID);
        return Optional.ofNullable(loja).map(e -> {
            return lojaCanonicoFactory.builderLoja(e);
        }).orElse(null);
    }

    public List<LojaCanonico> buscaLojas(FiltroWrapper filtro) {
        LojaFiltro lojaFiltro = (LojaFiltro) filtro.getFiltro();
        if (filtro.hasPaginacao()) {
            Long qtdRegistros = countRegistros(lojaFiltro);
            if (qtdRegistros == 0) {
                return Lists.newArrayList();
            }
            filtro.getPaginacao().setTotalRegistros(qtdRegistros);
            CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
            Root<Loja> root = criteria.from(Loja.class);
            criteria.distinct(true).select(root.get("lojaID"))
                    .where(aplicaFiltros(root, lojaFiltro, false)).orderBy(asc(root.get("lojaID")));
            List<Long> resultado = paginarResultado(criteria, filtro);
            if (resultado.isEmpty()) {
                return Lists.newArrayList();
            }
            lojaFiltro.setLojaID(resultado);
        }
        return buscaLojas(lojaFiltro);
    }

    private List<LojaCanonico> buscaLojas(LojaFiltro lojaFiltro) {
        CriteriaQuery<Loja> criteria = criteriaQuery(Loja.class);
        Root<Loja> root = criteria.from(Loja.class);
        criteria.distinct(true).select(root).where(aplicaFiltros(root, lojaFiltro, true))
                .orderBy(asc(root.get("lojaID")));
        TypedQuery<Loja> query = typedQuery(criteria);
        return lojaCanonicoFactory.lojasCanonico(query.getResultList());
    }

    private Long countRegistros(LojaFiltro lojaFiltro) {
        CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
        Root<Loja> root = criteria.from(Loja.class);
        criteria.select(criteriaBuilder().countDistinct(root.get("lojaID")))
                .where(aplicaFiltros(root, lojaFiltro, false));
        return typedQuery(criteria).getSingleResult();
    }

    private Predicate[] aplicaFiltros(Root<Loja> root, LojaFiltro lojaFiltro, boolean fetch) {
        CriteriaBuilder builder = criteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        if (lojaFiltro.hasLojaID()) {
            predicates.add(root.get("lojaID").in(lojaFiltro.getLojaID()));
        }
        if (lojaFiltro.hasNomeLoja()) {
            predicates.add(builder.like(builder.upper(root.get("nomeLoja")),
                    "%" + lojaFiltro.getNomeLoja().toUpperCase() + "%"));
        }
        if (lojaFiltro.hasCidade()) {
            predicates.add(builder.like(builder.upper(root.get("cidade")),
                    "%" + lojaFiltro.getCidade().toUpperCase() + "%"));
        }
        if (lojaFiltro.hasEstado()) {
            predicates.add(builder.equal(builder.upper(root.get("estado")), lojaFiltro.getEstado().toUpperCase()));
        }

        return predicates.toArray(new Predicate[0]);
    }

    public Loja salvaLoja(Loja loja) {
        return merge(loja);
    }
}
