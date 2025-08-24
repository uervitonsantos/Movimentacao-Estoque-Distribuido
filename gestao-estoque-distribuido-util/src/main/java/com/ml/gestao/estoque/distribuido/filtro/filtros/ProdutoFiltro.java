package com.ml.gestao.estoque.distribuido.filtro.filtros;

import com.ml.gestao.estoque.distribuido.filtro.Filtro;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoFiltro implements Filtro {

    private List<Long> produtoID;
    private String nomeProduto;
    private String categoria;
    private BigDecimal precoMin;
    private BigDecimal precoMax;

    public Boolean hasProdutoID() {
        return produtoID != null && !produtoID.isEmpty();
    }

    public Boolean hasNomeProduto() {
        return nomeProduto != null && !nomeProduto.isBlank();
    }

    public Boolean hasCategoria() {
        return categoria != null && !categoria.isBlank();
    }

    public Boolean hasPrecoMin() {
        return precoMin != null;
    }

    public Boolean hasPrecoMax() {
        return precoMax != null;
    }
}

