package com.ml.gestao.estoque.distribuido.util;

public enum CodigoValidacao {

    ERRO_VALIDACAO_PAGINA_INVALIDA("erro.validacao.pagina.invalida"),
    ERRO_VALIDACAO_QUANTIDADE_REGISTROS_INVALIDA("erro.validacao.quantidade.registros.invalida");

    private final String valor;

    private CodigoValidacao(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
