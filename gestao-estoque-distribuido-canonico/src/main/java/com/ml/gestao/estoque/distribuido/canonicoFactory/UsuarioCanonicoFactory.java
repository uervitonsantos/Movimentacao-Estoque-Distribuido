package com.ml.gestao.estoque.distribuido.canonicoFactory;

import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioCanonicoFactory {

    public UsuarioCanonico builderUsuario(Usuario usuario) {
        return Optional.ofNullable(usuario).map(entidade -> UsuarioCanonico.builder()
                .usuarioID(entidade.getUsuarioID())
                .nome(entidade.getNome())
                .email(entidade.getEmail())
                .senhaHash(entidade.getSenhaHash())
                .perfil(entidade.getPerfil())
                .build()).orElse(null);
    }

    public List<UsuarioCanonico> usuariosCanonico(List<Usuario> resultList) {
        return Optional.ofNullable(resultList).map(lista ->
                lista.stream().map(this::builderUsuario).collect(Collectors.toList())
        ).orElse(new ArrayList<>());
    }
}
