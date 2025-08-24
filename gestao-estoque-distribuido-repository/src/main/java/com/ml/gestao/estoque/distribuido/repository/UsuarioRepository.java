package com.ml.gestao.estoque.distribuido.repository;

import com.google.common.collect.Lists;
import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.canonicoFactory.UsuarioCanonicoFactory;
import com.ml.gestao.estoque.distribuido.canonicoFactory.UsuarioCanonicoFactory;
import com.ml.gestao.estoque.distribuido.entidade.Usuario;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;

import com.ml.gestao.estoque.distribuido.filtro.filtros.UsuarioFiltro;
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
public class UsuarioRepository extends GestaoEstoqueDistribuidoRepositorio {

    @Autowired
    private UsuarioCanonicoFactory usuarioCanonicoFactory;

    public Usuario busca(Long usuarioID) {
        return busca(Usuario.class, usuarioID);
    }

    public UsuarioCanonico buscaUsuario(Long usuarioID) {
        Usuario usuario = busca(usuarioID);
        return Optional.ofNullable(usuario).map(e -> usuarioCanonicoFactory.builderUsuario(e)).orElse(null);
    }

    public List<UsuarioCanonico> buscaUsuarios(FiltroWrapper filtro) {
        UsuarioFiltro usuarioFiltro = (UsuarioFiltro) filtro.getFiltro();
        if (filtro.hasPaginacao()) {
            Long qtdRegistros = countRegistros(usuarioFiltro);
            if (qtdRegistros == 0) {
                return Lists.newArrayList();
            }
            filtro.getPaginacao().setTotalRegistros(qtdRegistros);
            CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
            Root<Usuario> root = criteria.from(Usuario.class);
            criteria.distinct(true).select(root.get("usuarioID"))
                    .where(aplicaFiltros(root, usuarioFiltro, false)).orderBy(asc(root.get("usuarioID")));
            List<Long> resultado = paginarResultado(criteria, filtro);
            if (resultado.isEmpty()) {
                return Lists.newArrayList();
            }
            usuarioFiltro.setUsuarioID(resultado);
        }
        return buscaUsuarios(usuarioFiltro);
    }

    private List<UsuarioCanonico> buscaUsuarios(UsuarioFiltro usuarioFiltro) {
        CriteriaQuery<Usuario> criteria = criteriaQuery(Usuario.class);
        Root<Usuario> root = criteria.from(Usuario.class);
        criteria.distinct(true).select(root).where(aplicaFiltros(root, usuarioFiltro, true))
                .orderBy(asc(root.get("usuarioID")));
        TypedQuery<Usuario> query = typedQuery(criteria);
        return usuarioCanonicoFactory.usuariosCanonico(query.getResultList());
    }

    private Long countRegistros(UsuarioFiltro usuarioFiltro) {
        CriteriaQuery<Long> criteria = criteriaQuery(Long.class);
        Root<Usuario> root = criteria.from(Usuario.class);
        criteria.select(criteriaBuilder().countDistinct(root.get("usuarioID")))
                .where(aplicaFiltros(root, usuarioFiltro, false));
        return typedQuery(criteria).getSingleResult();
    }

    private Predicate[] aplicaFiltros(Root<Usuario> root, UsuarioFiltro usuarioFiltro, boolean fetch) {
        CriteriaBuilder builder = criteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();



        return predicates.toArray(new Predicate[0]);
    }

    public Usuario salvaUsuario(Usuario usuario) {
        return merge(usuario);
    }
}
