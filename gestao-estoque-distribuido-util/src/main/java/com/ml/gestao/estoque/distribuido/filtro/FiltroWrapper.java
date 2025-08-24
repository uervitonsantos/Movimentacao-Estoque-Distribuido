package com.ml.gestao.estoque.distribuido.filtro;


import com.ml.gestao.estoque.distribuido.enumerated.EnumCampos;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

public class FiltroWrapper implements Ordenador, Paginador {

    private final Filtro filtro;
    private final Optional<Paginacao> paginacao;
    private final List<Ordenacao> ordenacao;
    private final List<? extends EnumCampos> campos;

    public FiltroWrapper(Filtro filtro) {
        this(filtro, Optional.empty());
    }

    public FiltroWrapper(Filtro filtro, Optional<Paginacao> paginacao) {
        this(filtro, paginacao, Lists.newArrayList());
    }

    public FiltroWrapper(Filtro filtro, Optional<Paginacao> paginacao, List<Ordenacao> ordenacao) {
        this(filtro, paginacao, ordenacao, Lists.newArrayList());
    }

    public FiltroWrapper(Filtro filtro, Optional<Paginacao> paginacao, List<Ordenacao> ordenacao,
                         List<? extends EnumCampos> campos) {
        this.filtro = filtro;
        this.paginacao = paginacao;
        this.campos = Optional.ofNullable(campos).orElse(Lists.newArrayList());
        this.ordenacao = Optional.ofNullable(ordenacao).orElse(Lists.newArrayList());
    }

    public List<? extends EnumCampos> getCampos() {
        return campos;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public Optional<Paginacao> getPaginacaoOptional() {
        return paginacao;
    }

    @Override
    public Paginacao getPaginacao() {
        return paginacao.orElse(null);
    }

    @Override
    public List<Ordenacao> getOrdenacao() {
        return ordenacao;
    }

    @Override
    public boolean hasPaginacao() {
        return paginacao.isPresent();
    }

    @Override
    public boolean hasOrdenacao() {
        return !ordenacao.isEmpty();
    }

    public void addQuantidadeRegistros(Long quantidadeRegistros) {
        this.getPaginacao().setTotalRegistros(quantidadeRegistros);
    }
}
