package com.ml.gestao.estoque.distribuido.filtro;

public class Ordenacao {
    public static enum Ordem {
        ASC, DESC
    }

    private final CampoOrdenacao campo;
    private final Ordem ordem;

    public Ordenacao(CampoOrdenacao campo, Ordem ordem) {
        this.campo = campo;
        this.ordem = ordem;
    }

    public String getCampo() {
        return campo.getValor();
    }

    public Ordem getOrdem() {
        return ordem;
    }
}
