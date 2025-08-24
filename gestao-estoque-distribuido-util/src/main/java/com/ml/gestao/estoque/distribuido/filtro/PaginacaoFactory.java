package com.ml.gestao.estoque.distribuido.filtro;

import java.util.Optional;

public final class PaginacaoFactory {
    public static Optional<Paginacao> cria(Integer pagina,
                                           Integer quantidadeRegistros) {
        if (pagina != null && quantidadeRegistros != null) {
            return Optional.of(new Paginacao(pagina, quantidadeRegistros));
        }
        return Optional.empty();
    }

    public static Optional<Paginacao> cria(Integer pagina, Integer quantidadeRegistros, Boolean indUltimaPagina) {
        if (!Boolean.TRUE.equals(indUltimaPagina)) {
            return cria(pagina, quantidadeRegistros);
        }

        return Optional.of(new Paginacao(pagina, quantidadeRegistros, indUltimaPagina));
    }
}
