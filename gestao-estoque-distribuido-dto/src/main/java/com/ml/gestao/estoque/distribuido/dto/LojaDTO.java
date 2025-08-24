package com.ml.gestao.estoque.distribuido.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Loja", description = "Dados da Loja")
@XmlRootElement(name = "Loja")
@XmlAccessorType(XmlAccessType.FIELD)
public class LojaDTO {

    @Schema(description = "Identificador único da loja", example = "1")
    @XmlElement(nillable = false)
    private Long lojaID;

    @Schema(description = "Nome da loja", example = "Loja Central")
    @XmlElement(nillable = false)
    private String nome;

    @Schema(description = "Endereço da loja", example = "Rua das Flores, 123")
    @XmlElement(nillable = false)
    private String endereco;
}
