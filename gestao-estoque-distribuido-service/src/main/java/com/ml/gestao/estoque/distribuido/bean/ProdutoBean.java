package com.ml.gestao.estoque.distribuido.bean;

import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoBean {

    @Autowired
    private ProdutoService produtoService;

    public ProdutoCanonico buscaProduto(Long produtoID) {
        return produtoService.buscaProduto(produtoID);
    }

    public List<ProdutoCanonico> buscaProdutos(FiltroWrapper wrapper) {
        return produtoService.buscaProdutos(wrapper);
    }

    public ProdutoCanonico criaProduto(ProdutoCanonico canonico) {
        return produtoService.criaProduto(canonico);
    }

    public ProdutoCanonico editaProduto(ProdutoCanonico canonico) {
        Long editaProduto = produtoService.editaProduto(canonico);
        return buscaProduto(editaProduto);
    }

    public void remoceProduto(Long produtoID) {
        produtoService.removeProduto(produtoID);
    }
}
