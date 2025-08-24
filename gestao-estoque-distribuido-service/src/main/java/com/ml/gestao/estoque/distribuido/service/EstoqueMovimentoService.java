package com.ml.gestao.estoque.distribuido.service;

import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.entidade.EstoqueMovimento;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import com.ml.gestao.estoque.distribuido.entidade.Usuario;
import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.repository.EstoqueMovimentacaoRepository;
import com.ml.gestao.estoque.distribuido.repository.LojaRepository;
import com.ml.gestao.estoque.distribuido.repository.ProdutoRepository;
import com.ml.gestao.estoque.distribuido.repository.UsuarioRepository;
import com.ml.gestao.estoque.distribuido.util.MensagensValidacao;
import com.ml.gestao.estoque.distribuido.util.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueMovimentoService {

    @Autowired
    private EstoqueMovimentacaoRepository movimentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private LojaService lojaService;

    public EstoqueMovimentoCanonico buscaMovimento(Long movimentoID) {
        return Optional.ofNullable(movimentoRepository.buscaEstoqueMovimento(movimentoID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_VALIDACAO_COD_MOVIMENTACAO_NAO_ENCONTRADO.getValor()));
    }

    public EstoqueMovimento buscarMovimento(Long movimentoID) {
        return Optional.ofNullable(movimentoRepository.busca(movimentoID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_VALIDACAO_COD_MOVIMENTACAO_NAO_ENCONTRADO.getValor()));
    }

    public List<EstoqueMovimentoCanonico> buscaEstoqueMovimentos(FiltroWrapper filtro) {
        return movimentoRepository.buscaEstoqueMovimentos(filtro);
    }

    public EstoqueMovimentoCanonico criaMovimento(EstoqueMovimentoCanonico canonico) {
        validaDadosMovimento(canonico);
        produtoService.buscaProduto(canonico.getProduto().getProdutoID());
        lojaService.buscaLoja(canonico.getLoja().getLojaID());
        usuarioService.buscarUsuario(canonico.getUsuario().getUsuarioID());
        Long movimentoID = salvaMovimento(canonico);
        return buscaMovimento(movimentoID);
    }

    public Long editaMovimentacaoEstoque(EstoqueMovimentoCanonico canonico){
        EstoqueMovimento movimento = buscarMovimento(canonico.getMovimentoID());
        validaDadosMovimento(canonico);
        populaMovimentoEdicao(movimento, canonico);
        movimento = movimentoRepository.merge(movimento);
        return movimento.getMovimentoID();
    }

    private Long salvaMovimento(EstoqueMovimentoCanonico canonico) {
        EstoqueMovimento movimento = geraMovimento(canonico);
        populaMovimento(movimento, canonico);
        EstoqueMovimento salvo = movimentoRepository.salvaEstoqueMovimento(movimento);
        return salvo.getMovimentoID();
    }

    private EstoqueMovimento geraMovimento(EstoqueMovimentoCanonico canonico) {
        if (canonico.getMovimentoID() == null) {
            return new EstoqueMovimento();
        }
        return movimentoRepository.busca(canonico.getMovimentoID());
    }

    private void populaMovimentoEdicao(EstoqueMovimento movimento, EstoqueMovimentoCanonico canonico) {

        // ===== Produto =====
        Produto produto;
        if (canonico.getProduto().getProdutoID() != null) {
            // busca e atualiza
            produto = produtoRepository.busca(canonico.getProduto().getProdutoID());
            produto.setNome(canonico.getProduto().getNome());
            produto.setDescricao(canonico.getProduto().getDescricao());
            produto.setSku(canonico.getProduto().getSku());
            produto.setCategoria(canonico.getProduto().getCategoria());
            produto.setPreco(canonico.getProduto().getPreco());
        } else {
            // cria novo
            produto = new Produto();
            produto.setNome(canonico.getProduto().getNome());
            produto.setDescricao(canonico.getProduto().getDescricao());
            produto.setSku(canonico.getProduto().getSku());
            produto.setCategoria(canonico.getProduto().getCategoria());
            produto.setPreco(canonico.getProduto().getPreco());
            produto.setEstoqueAtual(0); // inicializa estoque
        }

        // ===== Loja =====
        Loja loja;
        if (canonico.getLoja().getLojaID() != null) {
            loja = lojaRepository.busca(canonico.getLoja().getLojaID());
            loja.setNome(canonico.getLoja().getNome());
            loja.setEndereco(canonico.getLoja().getEndereco());
        } else {
            loja = new Loja();
            loja.setNome(canonico.getLoja().getNome());
            loja.setEndereco(canonico.getLoja().getEndereco());
        }

        // ===== Usuário =====
        Usuario usuario;
        if (canonico.getUsuario().getUsuarioID() != null) {
            usuario = usuarioRepository.busca(canonico.getUsuario().getUsuarioID());
            usuario.setNome(canonico.getUsuario().getNome());
            usuario.setEmail(canonico.getUsuario().getEmail());
            usuario.setSenhaHash(canonico.getUsuario().getSenhaHash());
            usuario.setPerfil(canonico.getUsuario().getPerfil());
        } else {
            usuario = new Usuario();
            usuario.setNome(canonico.getUsuario().getNome());
            usuario.setEmail(canonico.getUsuario().getEmail());
            usuario.setSenhaHash(canonico.getUsuario().getSenhaHash());
            usuario.setPerfil(canonico.getUsuario().getPerfil());
        }

        // ===== Guarda os valores antigos para reverter =====
        TipoMovimento tipoAntigo = movimento.getTipoMovimento();
        Integer quantidadeAntiga = movimento.getQuantidade();

        // ===== Atualiza os dados principais =====
        movimento.setProduto(produto);
        movimento.setLoja(loja);
        movimento.setUsuario(usuario);
        movimento.setTipoMovimento(canonico.getTipoMovimento());
        movimento.setQuantidade(canonico.getQuantidade());
        movimento.setDataHora(
                canonico.getDataHora() != null ? canonico.getDataHora() : LocalDateTime.now()
        );

        // ===== Reverte o movimento antigo (se existir) =====
        if (tipoAntigo != null && quantidadeAntiga != null) {
            if (tipoAntigo == TipoMovimento.ENTRADA) {
                produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidadeAntiga);
            } else if (tipoAntigo == TipoMovimento.SAIDA) {
                produto.setEstoqueAtual(produto.getEstoqueAtual() + quantidadeAntiga);
            }
        }

        // ===== Aplica o movimento novo =====
        if (canonico.getTipoMovimento() == TipoMovimento.ENTRADA) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + canonico.getQuantidade());
        } else if (canonico.getTipoMovimento() == TipoMovimento.SAIDA) {
            if (produto.getEstoqueAtual() < canonico.getQuantidade()) {
                throw new ValidacaoException(MensagensValidacao.ERRO_ESTOQUE_INSUFICIENTE.getValor());
            }
            produto.setEstoqueAtual(produto.getEstoqueAtual() - canonico.getQuantidade());
        }

        // ===== Persiste as alterações =====
        produtoRepository.merge(produto);
        lojaRepository.merge(loja);
        usuarioRepository.merge(usuario);
    }


    private void populaMovimento(EstoqueMovimento movimento, EstoqueMovimentoCanonico canonico) {
        Produto produto = produtoRepository.busca(canonico.getProduto().getProdutoID());
        Loja loja = lojaRepository.busca(canonico.getLoja().getLojaID());
        Usuario usuario = usuarioRepository.busca(canonico.getUsuario().getUsuarioID());

        // Antes de atualizar, guarda os valores antigos
        TipoMovimento tipoAntigo = movimento.getTipoMovimento();
        Integer quantidadeAntiga = movimento.getQuantidade();

        // Atualiza os dados principais
        movimento.setProduto(produto);
        movimento.setLoja(loja);
        movimento.setUsuario(usuario);
        movimento.setTipoMovimento(canonico.getTipoMovimento());
        movimento.setQuantidade(canonico.getQuantidade());
        movimento.setDataHora(
                canonico.getDataHora() != null ? canonico.getDataHora() : LocalDateTime.now()
        );

        // Ajuste do estoque
        if (tipoAntigo != null && quantidadeAntiga != null) {
            // Reverte o movimento antigo
            if (tipoAntigo == TipoMovimento.ENTRADA) {
                produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidadeAntiga);
            } else if (tipoAntigo == TipoMovimento.SAIDA) {
                produto.setEstoqueAtual(produto.getEstoqueAtual() + quantidadeAntiga);
            }
        }

        // Aplica o movimento novo
        if (canonico.getTipoMovimento() == TipoMovimento.ENTRADA) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + canonico.getQuantidade());
        } else if (canonico.getTipoMovimento() == TipoMovimento.SAIDA) {
            if (produto.getEstoqueAtual() < canonico.getQuantidade()) {
                throw new ValidacaoException(MensagensValidacao.ERRO_ESTOQUE_INSUFICIENTE.getValor());
            }
            produto.setEstoqueAtual(produto.getEstoqueAtual() - canonico.getQuantidade());
        }

        produtoRepository.merge(produto);
    }

    private void validaDadosMovimento(EstoqueMovimentoCanonico canonico) {
        if (canonico.getProduto() == null || canonico.getProduto().getProdutoID() == null) {
            throw new ValidacaoException(MensagensValidacao.ERRO_PRODUTO_OBRIGATORIO.getValor());
        }
        if (canonico.getLoja() == null || canonico.getLoja().getLojaID() == null) {
            throw new ValidacaoException(MensagensValidacao.ERRO_LOJA_OBRIGATORIA.getValor());
        }
        if (canonico.getUsuario() == null || canonico.getUsuario().getUsuarioID() == null) {
            throw new ValidacaoException(MensagensValidacao.ERRO_USUARIO_NAO_ENCONTRADO.getValor());
        }
        if (canonico.getTipoMovimento() == null) {
            throw new ValidacaoException(MensagensValidacao.ERRO_TIPO_MOVIMENTO_OBRIGATORIO.getValor());
        }
        if (canonico.getQuantidade() == null || canonico.getQuantidade() <= 0) {
            throw new ValidacaoException(MensagensValidacao.ERRO_QUANTIDADE_ESTOQUE_INVALIDA.getValor());
        }
    }

    public void removeMovimento(Long movimentoID) {
        EstoqueMovimento movimento = buscarMovimento(movimentoID);
        movimentoRepository.remove(movimento);
    }
}
