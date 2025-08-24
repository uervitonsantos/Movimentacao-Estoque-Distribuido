package com.ml.gestao.estoque.distribuido.dtoFactory;

import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOFactory {


    public UsuarioDTO builderUsuarioDto(UsuarioCanonico usuario) {
        return Optional.ofNullable(usuario).map(entidade -> {
            return UsuarioDTO.builder()
                    .usuarioID(entidade.getUsuarioID())
                    .nome(entidade.getNome())
                    .email(entidade.getEmail())
                    .senhaHash(entidade.getSenhaHash())
                    .perfil(entidade.getPerfil())
                    .build();
        }).orElse(null);
    }

    public List<UsuarioDTO> usuariosDto(List<UsuarioCanonico> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderUsuarioDto(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<UsuarioDTO>());

    }

    public UsuarioCanonico builderUsuarioCanonico(UsuarioDTO usuario) {
        return Optional.ofNullable(usuario).map(entidade -> {
            return UsuarioCanonico.builder()
                    .usuarioID(entidade.getUsuarioID())
                    .nome(entidade.getNome())
                    .email(entidade.getEmail())
                    .senhaHash(entidade.getSenhaHash())
                    .perfil(entidade.getPerfil())
                    .build();
        }).orElse(null);
    }

    public List<UsuarioCanonico> usuariosCanonico(List<UsuarioDTO> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderUsuarioCanonico(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<UsuarioCanonico>());
    }
}
