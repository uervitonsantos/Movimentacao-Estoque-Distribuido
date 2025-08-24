package com.ml.gestao.estoque.distribuido.filtro.filtros;

import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import com.ml.gestao.estoque.distribuido.filtro.Filtro;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFiltro implements Filtro {

    private List<Long> usuarioID;
    private String nome;
    private String email;
    private PerfilUsuario perfil;

}

