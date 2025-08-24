package com.ml.gestao.estoque.distribuido.dtoFactory;

import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOFactory {


    public ProdutoDTO builderProdutoDto(ProdutoCanonico produto) {
        return Optional.ofNullable(produto).map(entidade -> {
            return ProdutoDTO.builder()
                    .produtoID(entidade.getProdutoID())
                    .nome(entidade.getNome())
                    .descricao(entidade.getDescricao())
                    .sku(entidade.getSku())
                    .categoria(entidade.getCategoria())
                    .preco(entidade.getPreco())
                    .estoqueAtual(entidade.getEstoqueAtual())
                    .build();
        }).orElse(null);
    }

    public List<ProdutoDTO> produtosDto(List<ProdutoCanonico> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderProdutoDto(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<ProdutoDTO>());

    }

    public ProdutoCanonico builderProdutoCanonico(ProdutoDTO produto) {
        return Optional.ofNullable(produto).map(entidade -> {
            return ProdutoCanonico.builder()
                    .produtoID(entidade.getProdutoID())
                    .nome(entidade.getNome())
                    .descricao(entidade.getDescricao())
                    .sku(entidade.getSku())
                    .categoria(entidade.getCategoria())
                    .preco(entidade.getPreco())
                    .estoqueAtual(entidade.getEstoqueAtual())
                    .build();
        }).orElse(null);
    }

    public List<ProdutoCanonico> produtosCanonico(List<ProdutoDTO> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderProdutoCanonico(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<ProdutoCanonico>());
    }
}
