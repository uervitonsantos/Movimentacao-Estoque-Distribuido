package com.ml.gestao.estoque.distribuido.bean;

import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LojaBean {

    @Autowired
    private LojaService lojaService;

    public LojaCanonico buscaLoja(Long lojaID) {
        return lojaService.buscaLoja(lojaID);
    }

    public List<LojaCanonico> buscaLojas(FiltroWrapper filtro) {
        return lojaService.buscaLojas(filtro);
    }

    public LojaCanonico criaLoja(LojaCanonico canonico) {
        return lojaService.criaLoja(canonico);
    }

    public LojaCanonico editaLoja(LojaCanonico canonico) {
        Long editaLoja = lojaService.editaLoja(canonico);
        return buscaLoja(editaLoja);
    }

    public void removeLoja(Long lojaID) {
        lojaService.removeLoja(lojaID);
    }
}
