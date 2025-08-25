package com.ml.gestao.estoque.distribuido.controllerImplements;

import com.ml.gestao.estoque.distribuido.bean.ProdutoBean;
import com.ml.gestao.estoque.distribuido.canonico.ProdutoCanonico;
import com.ml.gestao.estoque.distribuido.dto.ProdutoDTO;
import com.ml.gestao.estoque.distribuido.dtoFactory.ProdutoDTOFactory;
import com.ml.gestao.estoque.distribuido.filterDTO.ProdutoFiltroDTO;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.recurso.ProdutoSwagger;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import jakarta.ws.rs.BeanParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/produto")
public class ProdutoController implements ProdutoSwagger {

    private static final String PATH_ID = "/{produtoID}";

    @Autowired
    private ProdutoBean produtoBean;

    @Autowired
    private ProdutoDTOFactory produtoDTOFactory;

    @Override
    @GetMapping(PATH_ID)
    public ResponseEntity<ProdutoDTO> getProduto(@PathVariable(Resource.P_PRODUTO_ID) Long produtoID) {
        log.info("[LISTAGEM DO PRODUTO] | INICIO");
        ProdutoDTO dto = produtoDTOFactory.builderProdutoDto(produtoBean.buscaProduto(produtoID));
        log.info("[LISTAGEM DO PRODUTO] | FINALIZADO");
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getProdutos(@BeanParam ProdutoFiltroDTO filtro) {
        log.info("[LISTAGEM DO PRODUTO] | INICIO");
        FiltroWrapper wrapper = filtro.filtroWrapper();
        List<ProdutoCanonico> produtos = produtoBean.buscaProdutos(wrapper);
        log.info("[LISTAGEM DO PRODUTO] | FINALIZADO");
        return ResponseEntity.ok(produtoDTOFactory.produtosDto(produtos));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProdutoDTO> criaProduto(@RequestBody ProdutoDTO dto) {
        log.info("[CRIACAO DO PRODUTO] | INICIO");
        ProdutoCanonico canonico = produtoDTOFactory.builderProdutoCanonico(dto);
        ProdutoCanonico produtoSalvo = produtoBean.criaProduto(canonico);
        log.info("[CRIACAO DO PRODUTO] | FINALIZADO");
        return criaProdutoResponse(produtoSalvo);
    }

    @Override
    @PutMapping(PATH_ID)
    public ResponseEntity atualizaProduto(@PathVariable(Resource.P_PRODUTO_ID) Long produtoID, @RequestBody ProdutoDTO dto) {
        log.info("[ATUALIZACAO DO PRODUTO] | INICIO");
        dto.setProdutoID(produtoID);
        ProdutoCanonico canonico = produtoDTOFactory.builderProdutoCanonico(dto);
        ProdutoCanonico produtoAtualizado = produtoBean.editaProduto(canonico);
        log.info("[ATUALIZACAO DO PRODUTO] | FINALIZADO");
        return criaProdutoResponse(produtoAtualizado);
    }

    private ResponseEntity<ProdutoDTO> criaProdutoResponse(@RequestBody ProdutoCanonico produtoSalvo) {
        ProdutoDTO produtoDTO = produtoDTOFactory.builderProdutoDto(produtoBean.buscaProduto(produtoSalvo.getProdutoID()));
        return ResponseEntity.ok().body(produtoDTO);
    }

    @Override
    @DeleteMapping(PATH_ID)
    public ResponseEntity removeProduto(@PathVariable(Resource.P_PRODUTO_ID) Long produtoID) {
        log.info("[REMOCAO DO PRODUTO] | INICIO");
        produtoBean.remoceProduto(produtoID);
        log.info("[REMOCAO DO PRODUTO] | FINALIZADO");
        return ResponseEntity.noContent().build();
    }
}
