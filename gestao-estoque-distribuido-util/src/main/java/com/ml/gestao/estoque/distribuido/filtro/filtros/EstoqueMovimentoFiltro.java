package com.ml.gestao.estoque.distribuido.filtro.filtros;

import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import com.ml.gestao.estoque.distribuido.filtro.Filtro;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueMovimentoFiltro implements Filtro {

    private List<Long> movimentoID;
    private Long produtoID;
    private Long lojaID;
    private TipoMovimento tipoMovimento;
    private LocalDateTime data;
    private String emailUsuario;
    private Integer quantidadeMin;
    private Integer quantidadeMax;

    public Boolean hasMovimentoID() {
        return movimentoID != null && !movimentoID.isEmpty();
    }

    public Boolean hasProdutoID() {
        return produtoID != null;
    }

    public Boolean hasLojaID() {
        return lojaID != null;
    }

    public Boolean hasQuantidadeMin() {
        return quantidadeMin != null;
    }

    public Boolean hasQuantidadeMax() {
        return quantidadeMax != null;
    }
}

