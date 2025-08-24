package com.ml.gestao.estoque.distribuido.canonico;

import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueMovimentoCanonico {

    private Long movimentoID;
    private ProdutoCanonico produto;
    private LojaCanonico loja;
    private TipoMovimento tipoMovimento;
    private Integer quantidade;
    private LocalDateTime dataHora;
    private UsuarioCanonico usuario;
}
