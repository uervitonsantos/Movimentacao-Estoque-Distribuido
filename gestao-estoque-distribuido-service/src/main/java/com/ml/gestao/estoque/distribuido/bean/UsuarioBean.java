package com.ml.gestao.estoque.distribuido.bean;

import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioBean {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioCanonico buscaUsuario(Long usuarioID) {
        return usuarioService.buscaUsuario(usuarioID);
    }

    public List<UsuarioCanonico> buscaUsuarios(FiltroWrapper wrapper) {
        return usuarioService.buscaUsuarios(wrapper);
    }

    public UsuarioCanonico criaUsuario(UsuarioCanonico canonico) {
        return usuarioService.criaUsuario(canonico);
    }

    public UsuarioCanonico editaUsuario(UsuarioCanonico canonico) {
        Long editaUsuario = usuarioService.editaUsuario(canonico);
        return buscaUsuario(editaUsuario);
    }

    public void removeUsuario(Long usuarioID) {
        usuarioService.removeUsuario(usuarioID);
    }
}
