package com.ml.gestao.estoque.distribuido.service;

import com.google.common.base.Strings;
import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.repository.ProdutoRepository;
import com.ml.gestao.estoque.distribuido.util.MensagensValidacao;
import com.ml.gestao.estoque.distribuido.util.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoCanonico buscaProduto(Long produtoID) {
        return Optional.ofNullable(produtoRepository.buscaProduto(produtoID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_PRODUTO_NAO_ENCONTRADO.getValor()));
    }

    public Produto buscarProduto(Long produtoID) {
        return Optional.ofNullable(produtoRepository.busca(produtoID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_PRODUTO_NAO_ENCONTRADO.getValor()));
    }

    public List<ProdutoCanonico> buscaProdutos(FiltroWrapper filtro) {
        return produtoRepository.buscaProdutos(filtro);
    }

    public ProdutoCanonico criaProduto(ProdutoCanonico canonico) {
        validaIDProduto(canonico);
        validaDadosProduto(canonico);
        Long produto = salvaProduto(canonico);
        return buscaProduto(produto);
    }

    private void validaIDProduto(ProdutoCanonico canonico) {
        if (canonico.getProdutoID() != null) {
            ProdutoCanonico produto = produtoRepository.buscaProduto(canonico.getProdutoID());
            if (produto != null) {
                throw new ValidacaoException(MensagensValidacao.ERRO_PRODUTO_JA_EXISTE.getValor());
            }
        }
    }

    public Long editaProduto(ProdutoCanonico canonico) {
        Produto produto = buscarProduto(canonico.getProdutoID());
        validaDadosProduto(canonico);
        populaProduto(produto, canonico);
        produto = produtoRepository.merge(produto);
        return produto.getProdutoID();
    }

    private void populaProduto(Produto produto, ProdutoCanonico canonico) {
        produto.setNome(canonico.getNome());
        produto.setDescricao(canonico.getDescricao());
        produto.setSku(canonico.getSku());
        produto.setCategoria(canonico.getCategoria());
        produto.setPreco(canonico.getPreco());
        produto.setEstoqueAtual(canonico.getEstoqueAtual());
    }

    private void validaDadosProduto(ProdutoCanonico canonico) {
        if (Strings.isNullOrEmpty(canonico.getNome())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_NOME_PRODUTO_OBRIGATORIO.getValor());
        }
        if (Strings.isNullOrEmpty(canonico.getDescricao())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_DESCRICAO_PRODUTO_OBRIGATORIO.getValor());
        }
        if (Strings.isNullOrEmpty(canonico.getSku())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_SKU_PRODUTO_OBRIGATORIO.getValor());
        }
        if (canonico.getPreco() == null || canonico.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoException(MensagensValidacao.ERRO_PRECO_PRODUTO_INVALIDO.getValor());
        }
        if (canonico.getEstoqueAtual() == null || canonico.getEstoqueAtual() < 0) {
            throw new ValidacaoException(MensagensValidacao.ERRO_ESTOQUE_PRODUTO_OBRIGATORIO.getValor());
        }
    }

    private Long salvaProduto(ProdutoCanonico canonico) {
        Produto produto = geraProduto(canonico);
        populaProduto(produto, canonico);
        Produto produtoSalvo = produtoRepository.salvaProduto(produto);
        return produtoSalvo.getProdutoID();
    }

    private Produto geraProduto(ProdutoCanonico canonico) {
        if (canonico.getProdutoID() == null) {
            return new Produto();
        }
        return produtoRepository.busca(canonico.getProdutoID());
    }

    public void removeProduto(Long produtoID) {
        Produto produto = buscarProduto(produtoID);
        produtoRepository.remove(produto);
    }
}
