package com.ml.gestao.estoque.distribuido.enumerated;

import lombok.Getter;

@Getter
public enum PerfilUsuario {

    ADMIN("ADMIN"),
    FUNCIONARIO("FUNCIONARIO"),
    CLIENTE("CLIENTE");

    private final String valor;

    PerfilUsuario(String valor) {
        this.valor = valor;
    }

    public static PerfilUsuario findByCodigo(String valor) {
        for (PerfilUsuario value : PerfilUsuario.values()) {
            if (value.getValor().equalsIgnoreCase(valor.trim())) {
                return value;
            }
        }
        return null;
    }
}

