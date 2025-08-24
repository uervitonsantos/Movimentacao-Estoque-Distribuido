package com.ml.gestao.estoque.distribuido.canonico;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LojaCanonico {

    private Long lojaID;
    private String nome;
    private String endereco;
    private List<EstoqueMovimentoCanonico> movimentos;
}
