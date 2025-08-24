package com.ml.gestao.estoque.distribuido.dtoFactory;

import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.dto.LojaDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LojaDTOFactory {


    public LojaDTO builderLojaDto(LojaCanonico loja) {
        return Optional.ofNullable(loja).map(entidade -> {
            return LojaDTO.builder()
                    .lojaID(entidade.getLojaID())
                    .nome(entidade.getNome())
                    .endereco(entidade.getEndereco())
                    .build();
        }).orElse(null);
    }

    public List<LojaDTO> lojasDto(List<LojaCanonico> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderLojaDto(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<LojaDTO>());

    }

    public LojaCanonico builderLojaCanonico(LojaDTO loja) {
        return Optional.ofNullable(loja).map(entidade -> {
            return LojaCanonico.builder()
                    .lojaID(entidade.getLojaID())
                    .nome(entidade.getNome())
                    .endereco(entidade.getEndereco())
                    .build();
        }).orElse(null);
    }

    public List<LojaCanonico> lojasCanonico(List<LojaDTO> resultList) {
        return Optional.ofNullable(resultList).map(lista -> {
            return lista.stream().map(el -> builderLojaCanonico(el)).collect(Collectors.toList());
        }).orElse(new ArrayList<LojaCanonico>());
    }
}
