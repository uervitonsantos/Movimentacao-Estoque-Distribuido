package com.ml.gestao.estoque.distribuido.filterDTO;

import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.Paginacao;
import com.ml.gestao.estoque.distribuido.filtro.PaginacaoFactory;
import com.ml.gestao.estoque.distribuido.filtro.filtros.ProdutoFiltro;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.QueryParam;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Schema(name = "Filtro", description = "Filtro para os Dados do Produto")
@XmlRootElement(name = "Filtro")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProdutoFiltroDTO {

    @QueryParam(Resource.P_QUANTIDADE_REGISTROS)
    @Schema(description = "Quantidade de registros", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidadeRegistros;

    @QueryParam(Resource.P_PAGINA)
    @Schema(description = "Pagina", example = "1")
    @XmlElement(nillable = false)
    private Integer pagina;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Identificador único do produto", example = "1")
    @XmlElement(nillable = false)
    private List<Long> produtoID;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Nome do produto", example = "Teclado Mecânico")
    @XmlElement(nillable = false)
    private String nomeProduto;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Categoria do produto", example = "Informática")
    @XmlElement(nillable = false)
    private String categoria;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Preço do produto", example = "299.90")
    @XmlElement(nillable = false)
    private BigDecimal precoMin;

    @QueryParam(Resource.P_LOJA_ID)
    @Schema(description = "Preço do produto", example = "299.90")
    @XmlElement(nillable = false)
    private BigDecimal precoMax;

    public FiltroWrapper filtroWrapper() {
       ProdutoFiltro filtro = ProdutoFiltro.builder()
                .produtoID(produtoID)
                .nomeProduto(nomeProduto)
                .categoria(categoria)
                .precoMin(precoMin)
                .precoMax(precoMax)
                .build();
        return new FiltroWrapper(filtro, geraPaginacao());
    }

    private Optional<Paginacao> geraPaginacao() {
        return PaginacaoFactory.cria(pagina, quantidadeRegistros);
    }
}
