package com.ml.gestao.estoque.distribuido.canonicoFactory;

import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LojaCanonicoFactory {

    public LojaCanonico builderLoja(Loja loja) {
        return Optional.ofNullable(loja).map(entidade -> LojaCanonico.builder()
                .lojaID(entidade.getLojaID())
                .nome(entidade.getNome())
                .endereco(entidade.getEndereco())
                .build()).orElse(null);
    }

    public List<LojaCanonico> lojasCanonico(List<Loja> resultList) {
        return Optional.ofNullable(resultList).map(lista ->
                lista.stream().map(this::builderLoja).collect(Collectors.toList())
        ).orElse(new ArrayList<>());
    }
}