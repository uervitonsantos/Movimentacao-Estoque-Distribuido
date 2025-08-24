package com.ml.gestao.estoque.distribuido.dto;

import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Usuario", description = "Dados do Usuário")
@XmlRootElement(name = "Usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioDTO {

    @Schema(description = "Identificador único do usuário", example = "1")
    @XmlElement(nillable = false)
    private Long usuarioID;

    @Schema(description = "Nome do usuário", example = "João Silva")
    @XmlElement(nillable = false)
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@email.com")
    @XmlElement(nillable = false)
    private String email;

    @Schema(description = "Hash da senha")
    @XmlElement(nillable = false)
    private String senhaHash;

    @Schema(description = "Perfil do usuário")
    @XmlElement(nillable = false)
    private PerfilUsuario perfil;
}
