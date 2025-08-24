package com.ml.gestao.estoque.distribuido.filtro.filtros;

import com.ml.gestao.estoque.distribuido.filtro.Filtro;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LojaFiltro implements Filtro {

    private List<Long> lojaID;
    private String nomeLoja;
    private String cidade;
    private String estado;

    public Boolean hasLojaID() {
        return lojaID != null && !lojaID.isEmpty();
    }

    public Boolean hasNomeLoja() {
        return nomeLoja != null && !nomeLoja.isBlank();
    }

    public Boolean hasCidade() {
        return cidade != null && !cidade.isBlank();
    }

    public Boolean hasEstado() {
        return estado != null && !estado.isBlank();
    }
}

