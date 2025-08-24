package com.ml.gestao.estoque.distribuido.canonico;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCanonico {

    private Long produtoID;
    private String nome;
    private String descricao;
    private String sku;
    private String categoria;
    private BigDecimal preco;
    private Integer estoqueAtual;
    private List<EstoqueMovimentoCanonico> movimentos;
}
