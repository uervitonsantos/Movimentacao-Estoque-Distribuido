package com.ml.gestao.estoque.distribuido.dto;

import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityListeners;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Estoque Movimento", description = "Dados do movimento de estoque")
@XmlRootElement(name = "Estoque Movimento")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstoqueMovimentoDTO {

    @Schema(description = "Identificador único do movimento", example = "1")
    @XmlElement(nillable = false)
    private Long movimentoID;

    @Schema(description = "Produto relacionado ao movimento")
    @XmlElement(nillable = false)
    private ProdutoDTO produto;

    @Schema(description = "Loja relacionada ao movimento")
    @XmlElement(nillable = false)
    private LojaDTO loja;

    @Schema(description = "Tipo de movimento (ENTRADA ou SAIDA)")
    @XmlElement(nillable = false)
    private TipoMovimento tipoMovimento;

    @Schema(description = "Quantidade movimentada", example = "10")
    @XmlElement(nillable = false)
    private Integer quantidade;

    @Schema(description = "Data e hora do movimento")
    @XmlElement(nillable = false)
    private LocalDateTime dataHora;

    @Schema(description = "Usuário responsável pelo movimento")
    @XmlElement(nillable = false)
    private UsuarioDTO usuario;
}
