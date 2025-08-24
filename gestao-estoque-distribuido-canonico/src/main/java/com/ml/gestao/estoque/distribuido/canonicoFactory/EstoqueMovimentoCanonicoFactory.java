package com.ml.gestao.estoque.distribuido.canonicoFactory;

import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.entidade.EstoqueMovimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EstoqueMovimentoCanonicoFactory {

    @Autowired
    private ProdutoCanonicoFactory produtoCanonicoFactory;

    @Autowired
    private LojaCanonicoFactory lojaCanonicoFactory;

    @Autowired
    private UsuarioCanonicoFactory usuarioCanonicoFactory;

    public EstoqueMovimentoCanonico builderMovimento(EstoqueMovimento movimento) {
        return Optional.ofNullable(movimento).map(entidade -> EstoqueMovimentoCanonico.builder()
                .movimentoID(entidade.getMovimentoID())
                .produto(produtoCanonicoFactory.builderProduto(entidade.getProduto()))
                .loja(lojaCanonicoFactory.builderLoja(entidade.getLoja()))
                .tipoMovimento(entidade.getTipoMovimento())
                .quantidade(entidade.getQuantidade())
                .dataHora(entidade.getDataHora())
                .usuario(usuarioCanonicoFactory.builderUsuario(entidade.getUsuario()))
                .build()).orElse(null);
    }

    public List<EstoqueMovimentoCanonico> movimentosCanonico(List<EstoqueMovimento> resultList) {
        return Optional.ofNullable(resultList).map(lista ->
                lista.stream().map(this::builderMovimento).collect(Collectors.toList())
        ).orElse(new ArrayList<>());
    }
}
