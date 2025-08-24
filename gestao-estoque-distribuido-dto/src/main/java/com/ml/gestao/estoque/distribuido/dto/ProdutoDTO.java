package com.ml.gestao.estoque.distribuido.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Produto", description = "Dados do Produto")
@XmlRootElement(name = "Produto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProdutoDTO {

    @Schema(description = "Identificador único do produto", example = "1")
    @XmlElement(nillable = false)
    private Long produtoID;

    @Schema(description = "Nome do produto", example = "Notebook Dell Inspiron")
    @XmlElement(nillable = false)
    private String nome;

    @Schema(description = "Descricao do produto", example = "Notebook Dell Inspiron 15 polegadas, i7, 16GB RAM, SSD 512GB")
    @XmlElement(nillable = false)
    private String descricao;

    @Schema(description = "SKU do produto", example = "TEC1234")
    @XmlElement(nillable = false)
    private String sku;

    @Schema(description = "Categoria do produto", example = "Informática")
    @XmlElement(nillable = false)
    private String categoria;

    @Schema(description = "Preço do produto", example = "299.90")
    @XmlElement(nillable = false)
    private BigDecimal preco;

    @Schema(description = "Estoque atual do produto", example = "50")
    @XmlElement(nillable = false)
    private Integer estoqueAtual;

}
