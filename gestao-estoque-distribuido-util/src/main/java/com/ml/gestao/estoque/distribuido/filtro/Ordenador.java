package com.ml.gestao.estoque.distribuido.filtro;

import java.util.List;

public interface Ordenador {

    boolean hasOrdenacao();

    List<Ordenacao> getOrdenacao();
}
