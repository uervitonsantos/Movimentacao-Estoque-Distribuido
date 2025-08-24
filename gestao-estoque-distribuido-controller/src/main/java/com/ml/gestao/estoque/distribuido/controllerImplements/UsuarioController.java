package com.ml.gestao.estoque.distribuido.controllerImplements;

import com.ml.gestao.estoque.distribuido.bean.UsuarioBean;
import com.ml.gestao.estoque.distribuido.canonico.UsuarioCanonico;
import com.ml.gestao.estoque.distribuido.dto.UsuarioDTO;
import com.ml.gestao.estoque.distribuido.dtoFactory.UsuarioDTOFactory;
import com.ml.gestao.estoque.distribuido.filterDTO.UsuarioFiltroDTO;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.recurso.UsuarioSwagger;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import jakarta.ws.rs.BeanParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController implements UsuarioSwagger {

    private static final String PATH_ID = "/{usuarioID}";

    @Autowired
    private UsuarioBean usuarioBean;

    @Autowired
    private UsuarioDTOFactory usuarioDTOFactory;

    @Override
    @GetMapping(PATH_ID)
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(Resource.P_USUARIO_ID) Long usuarioID) {
        UsuarioDTO dto = usuarioDTOFactory.builderUsuarioDto(usuarioBean.buscaUsuario(usuarioID));
        return ResponseEntity.ok().body(dto);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getUsuarios(@BeanParam UsuarioFiltroDTO filtro) {
        FiltroWrapper wrapper = filtro.filtroWrapper();
        List<UsuarioCanonico> usuarios = usuarioBean.buscaUsuarios(wrapper);
        return ResponseEntity.ok(usuarioDTOFactory.usuariosDto(usuarios));
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioDTO> criaUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioCanonico canonico = usuarioDTOFactory.builderUsuarioCanonico(dto);
        UsuarioCanonico usuarioSalvo = usuarioBean.criaUsuario(canonico);
        return criaUsuarioResponse(usuarioSalvo);
    }

    @Override
    @PutMapping(PATH_ID)
    public ResponseEntity atualizaUsuario(@PathVariable(Resource.P_USUARIO_ID) Long usuarioID,
                                          @RequestBody UsuarioDTO dto) {
        dto.setUsuarioID(usuarioID);
        UsuarioCanonico canonico = usuarioDTOFactory.builderUsuarioCanonico(dto);
        UsuarioCanonico usuarioAtualizado = usuarioBean.editaUsuario(canonico);
        return criaUsuarioResponse(usuarioAtualizado);
    }

    private ResponseEntity<UsuarioDTO> criaUsuarioResponse(@RequestBody UsuarioCanonico usuarioSalvo) {
        UsuarioDTO usuarioDTO = usuarioDTOFactory.builderUsuarioDto(usuarioBean.buscaUsuario(usuarioSalvo.getUsuarioID()));
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @Override
    @DeleteMapping(PATH_ID)
    public ResponseEntity removeUsuario(@PathVariable(Resource.P_USUARIO_ID) Long usuarioID) {
        usuarioBean.removeUsuario(usuarioID);
        return ResponseEntity.noContent().build();
    }
}
