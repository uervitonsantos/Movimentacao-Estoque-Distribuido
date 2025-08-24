package com.ml.gestao.estoque.distribuido.controllerImplements;

import com.ml.gestao.estoque.distribuido.bean.EstoqueMovimentacaoBean;
import com.ml.gestao.estoque.distribuido.canonico.EstoqueMovimentoCanonico;
import com.ml.gestao.estoque.distribuido.dto.EstoqueMovimentoDTO;
import com.ml.gestao.estoque.distribuido.dtoFactory.EstoqueMovimentoFactory;
import com.ml.gestao.estoque.distribuido.filterDTO.EstoqueMovimentoFiltroDTO;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.recurso.EstoqueMovimentoSwagger;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import jakarta.ws.rs.BeanParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/estoque")
public class EstoqueMovimentoController implements EstoqueMovimentoSwagger {

    private static final String PATH_ID = "/{movimentoID}";

    @Autowired
    private EstoqueMovimentacaoBean estoqueMovimentoBean;

    @Autowired
    private EstoqueMovimentoFactory estoqueMovimentoFactory;

    @Override
    @GetMapping(PATH_ID)
    public ResponseEntity<EstoqueMovimentoDTO> getMovimento(@PathVariable(Resource.P_ESTOQUE_MOVIMENTO_ID) Long movimentoID) {
        log.info("[LISTAGEM DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        EstoqueMovimentoDTO dto = estoqueMovimentoFactory
                .builderEstoqueMovimentoDto(estoqueMovimentoBean.buscaEstoqueMovimento(movimentoID));
        log.info("[LISTAGEM DA MOVIMENTACAO NO ESTOQUE] | FINALIZADO");
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EstoqueMovimentoDTO>> getMovimentos(@BeanParam EstoqueMovimentoFiltroDTO filtro) {
        log.info("[LISTAGEM DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        FiltroWrapper wrapper = filtro.filtroWrapper();
        List<EstoqueMovimentoCanonico> movimentos = estoqueMovimentoBean.buscaEstoquesMovimentos(wrapper);
        log.info("[LISTAGEM DA MOVIMENTACAO NO ESTOQUE] | FINALIZADO");
        return ResponseEntity.ok(estoqueMovimentoFactory.estoqueMovimentosDto(movimentos));
    }

    @Override
    @PostMapping
    public ResponseEntity<EstoqueMovimentoDTO> criaMovimento(@RequestBody EstoqueMovimentoDTO dto) {
        log.info("[INSERCAO DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        EstoqueMovimentoCanonico canonico = estoqueMovimentoFactory.builderEstoqueMovimentoCanonico(dto);
        EstoqueMovimentoCanonico movimentoSalvo = estoqueMovimentoBean.criaEstoqueMovimento(canonico);
        log.info("[INSERCAO DA MOVIMENTACAO NO ESTOQUE] | FINALIZADO");
        return criaEstoqueMovimentoResponse(movimentoSalvo);
    }

    @Override
    @PutMapping(PATH_ID)
    public ResponseEntity atualizaMovimento(
            @PathVariable(Resource.P_ESTOQUE_MOVIMENTO_ID) Long movimentoID,
            @RequestBody EstoqueMovimentoDTO dto) {
        log.info("[ATUALIZACAO DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        dto.setMovimentoID(movimentoID);
        EstoqueMovimentoCanonico canonico = estoqueMovimentoFactory.builderEstoqueMovimentoCanonico(dto);
        EstoqueMovimentoCanonico movimentoAtualizado = estoqueMovimentoBean.editaEstoqueMovimento(canonico);
        log.info("[ATUALIZACAO DA MOVIMENTACAO NO ESTOQUE] | FINALIZADO");
        return criaEstoqueMovimentoResponse(movimentoAtualizado);
    }

    private ResponseEntity<EstoqueMovimentoDTO> criaEstoqueMovimentoResponse(
            @RequestBody EstoqueMovimentoCanonico movimentoSalvo) {
        log.info("[CRIACAO DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        EstoqueMovimentoDTO movimentoDTO = estoqueMovimentoFactory
                .builderEstoqueMovimentoDto(
                        estoqueMovimentoBean.buscaEstoqueMovimento(movimentoSalvo.getMovimentoID())
                );
        log.info("[CRIACAO DA MOVIMENTACAO NO ESTOQUE] | FINALIZADO");
        return ResponseEntity.ok().body(movimentoDTO);
    }

    @Override
    @DeleteMapping(PATH_ID)
    public ResponseEntity removeMovimento(
            @PathVariable(Resource.P_ESTOQUE_MOVIMENTO_ID) Long movimentoID) {
        log.info("[REMOCAO DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        estoqueMovimentoBean.removeEstoqueMovimento(movimentoID);
        log.info("[REMOCAO DA MOVIMENTACAO NO ESTOQUE] | INICIO");
        return ResponseEntity.noContent().build();
    }
}
