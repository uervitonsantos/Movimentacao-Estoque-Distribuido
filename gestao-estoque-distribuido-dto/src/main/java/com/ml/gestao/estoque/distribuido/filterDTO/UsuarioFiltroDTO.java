package com.ml.gestao.estoque.distribuido.filterDTO;

import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.Paginacao;
import com.ml.gestao.estoque.distribuido.filtro.PaginacaoFactory;
import com.ml.gestao.estoque.distribuido.filtro.filtros.UsuarioFiltro;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.QueryParam;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Schema(name = "Filtro", description = "Filtro para os Dados do Usuário")
@XmlRootElement(name = "Filtro")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioFiltroDTO {

    @QueryParam(Resource.P_QUANTIDADE_REGISTROS)
    @Schema(description = "Quantidade de registros", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidadeRegistros;

    @QueryParam(Resource.P_PAGINA)
    @Schema(description = "Pagina", example = "1")
    @XmlElement(nillable = false)
    private Integer pagina;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Identificador único do usuário", example = "1")
    @XmlElement(nillable = false)
    private List<Long> usuarioID;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Nome do usuário", example = "João Silva")
    @XmlElement(nillable = false)
    private String nome;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Email do usuário", example = "joao.silva@email.com")
    @XmlElement(nillable = false)
    private String email;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Papel do usuário")
    @XmlElement(nillable = false)
    private PerfilUsuario perfil;

    public FiltroWrapper filtroWrapper() {
        UsuarioFiltro filtro = UsuarioFiltro.builder()
                .usuarioID(usuarioID)
                .nome(nome)
                .email(email)
                .perfil(perfil)
                .build();
        return new FiltroWrapper(filtro, geraPaginacao());
    }

    private Optional<Paginacao> geraPaginacao() {
        return PaginacaoFactory.cria(pagina, quantidadeRegistros);
    }
}
