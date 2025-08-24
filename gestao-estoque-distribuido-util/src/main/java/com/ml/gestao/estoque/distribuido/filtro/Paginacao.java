package com.ml.gestao.estoque.distribuido.filtro;



import com.ml.gestao.estoque.distribuido.util.CodigoValidacao;
import com.ml.gestao.estoque.distribuido.util.exception.ValidacaoException;

import java.util.Optional;

public class Paginacao {

    public static final int PAGINA_INVALIDA = 0;
    public static final int PAGINA_MINIMA_VALIDA = 1;
    public static Integer LIMITE_MAX_PAGINACAO = 1000;
    private final int quantidadeRegistros;
    private int pagina;
    private Boolean indUltimaPagina;
    private Long totalRegistros = 0l;

    Paginacao(int pagina, int quantidadeRegistros) {
        if (pagina < PAGINA_MINIMA_VALIDA) {
            throw new ValidacaoException(
                    String.valueOf(CodigoValidacao.ERRO_VALIDACAO_PAGINA_INVALIDA));
        }

        validaQuantidadeRegistros(quantidadeRegistros);
        this.pagina = pagina;
        this.quantidadeRegistros = quantidadeRegistros;
    }


    Paginacao(Integer pagina, Integer quantidadeRegistros, Boolean indUltimaPagina) {
        validaQuantidadeRegistros(quantidadeRegistros);
        this.pagina = Optional.ofNullable(pagina).orElse(PAGINA_MINIMA_VALIDA);
        this.quantidadeRegistros = Optional.ofNullable(quantidadeRegistros).orElse(10);
        this.indUltimaPagina = Optional.ofNullable(indUltimaPagina).orElse(true);
    }

    private static void validaQuantidadeRegistros(int quantidadeRegistros) {
        if (quantidadeRegistros < PAGINA_MINIMA_VALIDA || quantidadeRegistros > LIMITE_MAX_PAGINACAO) {
            throw new ValidacaoException(String.valueOf(CodigoValidacao.ERRO_VALIDACAO_QUANTIDADE_REGISTROS_INVALIDA));
        }
    }

    public int getPagina() {
        return pagina;
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public int getPrimeiroRegistro() {
        return (pagina - PAGINA_MINIMA_VALIDA) * quantidadeRegistros;
    }

    public int getUltimoRegistro() {
        return getPrimeiroRegistro() + getQuantidadeRegistros();
    }

    public Long getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros) {
        if (solicitouUltimaPagina()) {
            int novaPagina = (int) Math.ceil(Double.valueOf(Optional.ofNullable(totalRegistros).orElse(1l)) / quantidadeRegistros);
            this.pagina = novaPagina <= PAGINA_INVALIDA ? PAGINA_MINIMA_VALIDA : novaPagina;
        }
        this.totalRegistros = totalRegistros;
    }

    public boolean hasMais() {
        return totalRegistros > pagina * quantidadeRegistros;
    }

    public int getProxima() {
        return pagina + PAGINA_MINIMA_VALIDA;
    }

    public boolean solicitouUltimaPagina() {
        return Boolean.TRUE.equals(indUltimaPagina);
    }

}
