package com.ml.gestao.estoque.distribuido.controllerImplements;

import com.ml.gestao.estoque.distribuido.bean.LojaBean;
import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.dto.LojaDTO;
import com.ml.gestao.estoque.distribuido.dtoFactory.LojaDTOFactory;
import com.ml.gestao.estoque.distribuido.filterDTO.LojaFiltroDTO;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.recurso.LojaSwagger;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import jakarta.ws.rs.BeanParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/loja")
public class LojaController implements LojaSwagger {

    private static final String PATH_ID = "/{lojaID}";

    @Autowired
    private LojaBean lojaBean;

    @Autowired
    private LojaDTOFactory lojaDTOFactory;

    @Override
    @GetMapping(PATH_ID)
    public ResponseEntity<LojaDTO> getLoja(@PathVariable(Resource.P_LOJA_ID) Long lojaID) {
        LojaDTO dto = lojaDTOFactory.builderLojaDto(lojaBean.buscaLoja(lojaID));
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LojaDTO>> getLojas(@BeanParam LojaFiltroDTO filtro) {
        FiltroWrapper wrapper = filtro.filtroWrapper();
        List<LojaCanonico> lojas = lojaBean.buscaLojas(wrapper);
        return ResponseEntity.ok(lojaDTOFactory.lojasDto(lojas));
    }

    @Override
    @PostMapping
    public ResponseEntity<LojaDTO> criaLoja(@RequestBody LojaDTO dto) {
       LojaCanonico canonico = lojaDTOFactory.builderLojaCanonico(dto);
       LojaCanonico lojaSalva = lojaBean.criaLoja(canonico);
       return criaLojaResponse(lojaSalva);
    }

    @Override
    @PutMapping(PATH_ID)
    public ResponseEntity atualizaLoja(@PathVariable(Resource.P_LOJA_ID) Long lojaID, @RequestBody LojaDTO dto) {
        dto.setLojaID(lojaID);
        LojaCanonico canonico = lojaDTOFactory.builderLojaCanonico(dto);
        LojaCanonico lojaAtualizada = lojaBean.editaLoja(canonico);
        return criaLojaResponse(lojaAtualizada);
    }

    private ResponseEntity<LojaDTO> criaLojaResponse(@RequestBody LojaCanonico lojaSalva) {
        LojaDTO lojaDTO = lojaDTOFactory.builderLojaDto(lojaBean.buscaLoja(lojaSalva.getLojaID()));
        return ResponseEntity.ok().body(lojaDTO);
    }

    @Override
    @DeleteMapping(PATH_ID)
    public ResponseEntity removeLoja(@PathVariable(Resource.P_LOJA_ID) Long lojaID) {
        lojaBean.removeLoja(lojaID);
        return ResponseEntity.noContent().build();
    }
}
