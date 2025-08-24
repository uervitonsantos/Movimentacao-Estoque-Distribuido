package com.ml.gestao.estoque.distribuido.service;

import com.google.common.base.Strings;
import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Usuario;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.repository.UsuarioRepository;
import com.ml.gestao.estoque.distribuido.util.MensagensValidacao;
import com.ml.gestao.estoque.distribuido.util.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioCanonico buscaUsuario(Long usuarioID) {
        return Optional.ofNullable(usuarioRepository.buscaUsuario(usuarioID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_USUARIO_NAO_ENCONTRADO.getValor()));
    }

    public Usuario buscarUsuario(Long usuarioID) {
        return Optional.ofNullable(usuarioRepository.busca(usuarioID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_USUARIO_NAO_ENCONTRADO.getValor()));
    }

    public List<UsuarioCanonico> buscaUsuarios(FiltroWrapper filtro) {
        return usuarioRepository.buscaUsuarios(filtro);
    }

    public UsuarioCanonico criaUsuario(UsuarioCanonico canonico) {
        validaIDUsuario(canonico);
        validaDadosUsuario(canonico);
        Long usuarioID = salvaUsuario(canonico);
        return buscaUsuario(usuarioID);
    }

    private void validaIDUsuario(UsuarioCanonico canonico) {
        if (canonico.getUsuarioID() != null) {
            UsuarioCanonico usuarioExistente = usuarioRepository.buscaUsuario(canonico.getUsuarioID());
            if (usuarioExistente != null) {
                throw new ValidacaoException(MensagensValidacao.ERRO_USUARIO_JA_EXISTE.getValor());
            }
        }
    }

    public Long editaUsuario(UsuarioCanonico canonico) {
        Usuario usuario = buscarUsuario(canonico.getUsuarioID());
        validaDadosUsuario(canonico);
        populaUsuario(usuario, canonico);
        usuario = usuarioRepository.merge(usuario);
        return usuario.getUsuarioID();
    }

    private void populaUsuario(Usuario usuario, UsuarioCanonico canonico) {
        usuario.setNome(canonico.getNome());
        usuario.setEmail(canonico.getEmail());
        usuario.setSenhaHash(canonico.getSenhaHash());
        usuario.setPerfil(canonico.getPerfil());
    }

    private void validaDadosUsuario(UsuarioCanonico canonico) {
        if (Strings.isNullOrEmpty(canonico.getNome())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_NOME_USUARIO_OBRIGATORIO.getValor());
        }
        if (Strings.isNullOrEmpty(canonico.getEmail())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_EMAIL_USUARIO_OBRIGATORIO.getValor());
        }
        if (Strings.isNullOrEmpty(canonico.getSenhaHash())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_SENHA_USUARIO_OBRIGATORIO.getValor());
        }
        if (canonico.getPerfil() == null) {
            throw new ValidacaoException(MensagensValidacao.ERRO_PERFIL_USUARIO_OBRIGATORIO.getValor());
        }
    }

    private Long salvaUsuario(UsuarioCanonico canonico) {
        Usuario usuario = geraUsuario(canonico);
        populaUsuario(usuario, canonico);
        Usuario salvo = usuarioRepository.salvaUsuario(usuario);
        return salvo.getUsuarioID();
    }

    private Usuario geraUsuario(UsuarioCanonico canonico) {
        if (canonico.getUsuarioID() == null) {
            return new Usuario();
        }
        return usuarioRepository.busca(canonico.getUsuarioID());
    }

    public void removeUsuario(Long usuarioID) {
        Usuario usuario = buscarUsuario(usuarioID);
        usuarioRepository.remove(usuario);
    }
}
