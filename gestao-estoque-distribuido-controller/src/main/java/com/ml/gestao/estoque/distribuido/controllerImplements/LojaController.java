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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        log.info("[LISTAGEM DA LOJA] | INICIO");
        LojaDTO dto = lojaDTOFactory.builderLojaDto(lojaBean.buscaLoja(lojaID));
        log.info("[LISTAGEM DA LOJA] | FINALIZADO");
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LojaDTO>> getLojas(@BeanParam LojaFiltroDTO filtro) {
        log.info("[LISTAGEM DA LOJA] | INICIO");
        FiltroWrapper wrapper = filtro.filtroWrapper();
        List<LojaCanonico> lojas = lojaBean.buscaLojas(wrapper);
        log.info("[LISTAGEM DA LOJA] | FINALIZADO");
        return ResponseEntity.ok(lojaDTOFactory.lojasDto(lojas));
    }

    @Override
    @PostMapping
    public ResponseEntity<LojaDTO> criaLoja(@RequestBody LojaDTO dto) {
        log.info("[CRIACAO DA LOJA] | INICIO");
        LojaCanonico canonico = lojaDTOFactory.builderLojaCanonico(dto);
        LojaCanonico lojaSalva = lojaBean.criaLoja(canonico);
        log.info("[CRIACAO DA LOJA] | FINALIZADO");
        return criaLojaResponse(lojaSalva);
    }

    @Override
    @PutMapping(PATH_ID)
    public ResponseEntity atualizaLoja(@PathVariable(Resource.P_LOJA_ID) Long lojaID, @RequestBody LojaDTO dto) {
        log.info("[ATUALIZACAO DA LOJA] | INICIO");
        dto.setLojaID(lojaID);
        LojaCanonico canonico = lojaDTOFactory.builderLojaCanonico(dto);
        LojaCanonico lojaAtualizada = lojaBean.editaLoja(canonico);
        log.info("[ATUALIZACAO DA LOJA] | FINALIZADO");
        return criaLojaResponse(lojaAtualizada);
    }

    private ResponseEntity<LojaDTO> criaLojaResponse(@RequestBody LojaCanonico lojaSalva) {
        LojaDTO lojaDTO = lojaDTOFactory.builderLojaDto(lojaBean.buscaLoja(lojaSalva.getLojaID()));
        return ResponseEntity.ok().body(lojaDTO);
    }

    @Override
    @DeleteMapping(PATH_ID)
    public ResponseEntity removeLoja(@PathVariable(Resource.P_LOJA_ID) Long lojaID) {
        log.info("[REMOCAO DA LOJA] | INICIO");
        lojaBean.removeLoja(lojaID);
        log.info("[REMOCAO DA LOJA] | FINALIZADO");
        return ResponseEntity.noContent().build();
    }
}
