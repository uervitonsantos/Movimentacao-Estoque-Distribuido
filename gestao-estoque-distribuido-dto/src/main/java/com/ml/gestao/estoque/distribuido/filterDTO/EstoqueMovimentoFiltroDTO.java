package com.ml.gestao.estoque.distribuido.filterDTO;

import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.Paginacao;
import com.ml.gestao.estoque.distribuido.filtro.PaginacaoFactory;
import com.ml.gestao.estoque.distribuido.filtro.filtros.EstoqueMovimentoFiltro;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.QueryParam;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Schema(name = "Filtro", description = "Filtro para os Dados da Movimentação do Estoque")
@XmlRootElement(name = "Filtro")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstoqueMovimentoFiltroDTO {

    @QueryParam(Resource.P_QUANTIDADE_REGISTROS)
    @Schema(description = "Quantidade de registros", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidadeRegistros;

    @QueryParam(Resource.P_PAGINA)
    @Schema(description = "Pagina", example = "1")
    @XmlElement(nillable = false)
    private Integer pagina;

    @QueryParam(Resource.P_ESTOQUE_MOVIMENTO_ID)
    @Schema(description = "Identificador único do movimento", example = "1")
    @XmlElement(nillable = false)
    private List<Long> movimentoID;

    @QueryParam(Resource.P_PRODUTO_ID)
    @Schema(description = "Produto relacionado ao movimento")
    @XmlElement(nillable = false)
    private Long produtoID;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Loja relacionada ao movimento")
    @XmlElement(nillable = false)
    private Long lojaID;

    @QueryParam(Resource.P_TIPO_MOVIMENTO)
    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private TipoMovimento tipoMovimento;

    @QueryParam(Resource.P_DATA_MOVIMENTO)
    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private LocalDateTime data;

    @QueryParam(Resource.EMAIL_USUARIO)
    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private String emailUsuario;

    @QueryParam(Resource.P_QUANTIDADE_MIN)
    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidadeMin;

    @QueryParam(Resource.P_QUANTIDADE_MAX)
    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidadeMax;

    public FiltroWrapper filtroWrapper() {
        EstoqueMovimentoFiltro filtro = EstoqueMovimentoFiltro.builder()
                .movimentoID(movimentoID)
                .produtoID(produtoID)
                .lojaID(lojaID)
                .tipoMovimento(tipoMovimento)
                .data(data)
                .emailUsuario(emailUsuario)
                .quantidadeMin(quantidadeMin)
                .quantidadeMax(quantidadeMax)
                .build();
        return new FiltroWrapper(filtro, geraPaginacao());
    }

    private Optional<Paginacao> geraPaginacao() {
        return PaginacaoFactory.cria(pagina, quantidadeRegistros);
    }
}
