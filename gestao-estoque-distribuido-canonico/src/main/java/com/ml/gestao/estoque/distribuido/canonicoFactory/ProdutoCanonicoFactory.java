package com.ml.gestao.estoque.distribuido.canonicoFactory;

import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProdutoCanonicoFactory {

    public ProdutoCanonico builderProduto(Produto produto) {
        return Optional.ofNullable(produto).map(entidade -> ProdutoCanonico.builder()
                .produtoID(entidade.getProdutoID())
                .nome(entidade.getNome())
                .descricao(entidade.getDescricao())
                .sku(entidade.getSku())
                .categoria(entidade.getCategoria())
                .preco(entidade.getPreco())
                .estoqueAtual(entidade.getEstoqueAtual())
                .build()).orElse(null);
    }

    public List<ProdutoCanonico> produtosCanonico(List<Produto> resultList) {
        return Optional.ofNullable(resultList).map(lista ->
                lista.stream().map(this::builderProduto).collect(Collectors.toList())
        ).orElse(new ArrayList<>());
    }
}
