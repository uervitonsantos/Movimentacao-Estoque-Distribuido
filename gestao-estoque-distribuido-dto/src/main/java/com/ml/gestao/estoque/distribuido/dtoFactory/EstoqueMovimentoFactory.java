package com.ml.gestao.estoque.distribuido.dtoFactory;

import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.dto.EstoqueMovimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EstoqueMovimentoFactory {

    @Autowired
    private ProdutoDTOFactory produtoDTOFactory;

    @Autowired
    private LojaDTOFactory lojaDTOFactory;

    @Autowired
    private UsuarioDTOFactory usuarioDTOFactory;


    public EstoqueMovimentoDTO builderEstoqueMovimentoDto(EstoqueMovimentoCanonico estoqueMovimento) {
        return Optional.ofNullable(estoqueMovimento).map(entidade -> {
            return EstoqueMovimentoDTO.builder()
                    .movimentoID(entidade.getMovimentoID())
                    .produto(produtoDTOFactory.builderProdutoDto(entidade.getProduto()))
                    .loja(lojaDTOFactory.builderLojaDto(entidade.getLoja()))
                    .tipoMovimento(entidade.getTipoMovimento())
                    .quantidade(entidade.getQuantidade())
                    .dataHora(entidade.getDataHora())
                    .usuario(usuarioDTOFactory.builderUsuarioDto(entidade.getUsuario()))
                    .build();
        }).orElse(null);
    }

    public List<EstoqueMovimentoDTO> estoqueMovimentosDto(List<EstoqueMovimentoCanonico> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderEstoqueMovimentoDto(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<EstoqueMovimentoDTO>());

    }

    public EstoqueMovimentoCanonico builderEstoqueMovimentoCanonico(EstoqueMovimentoDTO estoqueMovimento) {
        return Optional.ofNullable(estoqueMovimento).map(entidade -> {
            return EstoqueMovimentoCanonico.builder()
                    .movimentoID(entidade.getMovimentoID())
                    .produto(produtoDTOFactory.builderProdutoCanonico(entidade.getProduto()))
                    .loja(lojaDTOFactory.builderLojaCanonico(entidade.getLoja()))
                    .tipoMovimento(entidade.getTipoMovimento())
                    .quantidade(entidade.getQuantidade())
                    .dataHora(entidade.getDataHora())
                    .usuario(usuarioDTOFactory.builderUsuarioCanonico(entidade.getUsuario()))
                    .build();
        }).orElse(null);
    }

    public List<EstoqueMovimentoCanonico> estoqueMovimentosCanonico(List<EstoqueMovimentoDTO> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderEstoqueMovimentoCanonico(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<EstoqueMovimentoCanonico>());
    }
}
