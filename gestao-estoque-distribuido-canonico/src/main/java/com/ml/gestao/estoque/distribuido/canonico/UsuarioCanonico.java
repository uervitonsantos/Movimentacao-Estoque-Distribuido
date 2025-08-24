package com.ml.gestao.estoque.distribuido.canonico;

import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCanonico {

    private Long usuarioID;
    private String nome;
    private String email;
    private String senhaHash;
    private PerfilUsuario perfil;
    private List<EstoqueMovimentoCanonico> movimentos;
}
