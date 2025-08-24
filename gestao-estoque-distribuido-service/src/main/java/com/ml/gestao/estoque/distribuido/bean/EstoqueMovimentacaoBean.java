package com.ml.gestao.estoque.distribuido.bean;

import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.service.EstoqueMovimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstoqueMovimentacaoBean {

    @Autowired
    private EstoqueMovimentoService estoqueMovimentoService;

    public EstoqueMovimentoCanonico buscaEstoqueMovimento(Long movimentoID) {
        return estoqueMovimentoService.buscaMovimento(movimentoID);
    }

    public List<EstoqueMovimentoCanonico> buscaEstoquesMovimentos(FiltroWrapper wrapper) {
        return estoqueMovimentoService.buscaEstoqueMovimentos(wrapper);
    }

    public EstoqueMovimentoCanonico criaEstoqueMovimento(EstoqueMovimentoCanonico canonico) {
        return estoqueMovimentoService.criaMovimento(canonico);
    }

    public EstoqueMovimentoCanonico editaEstoqueMovimento(EstoqueMovimentoCanonico canonico) {
        Long editaMovitentacao = estoqueMovimentoService.editaMovimentacaoEstoque(canonico);
        return buscaEstoqueMovimento(editaMovitentacao);
    }

    public void removeEstoqueMovimento(Long movimentoID) {
        estoqueMovimentoService.removeMovimento(movimentoID);
    }
}
