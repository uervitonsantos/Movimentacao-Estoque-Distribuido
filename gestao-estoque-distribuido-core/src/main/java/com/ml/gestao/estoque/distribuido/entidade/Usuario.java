package com.ml.gestao.estoque.distribuido.entidade;

import com.ml.gestao.estoque.distribuido.converter.PerfilUsuarioConverter;
import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    @Id
    @Column(name = "USUARIO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long usuarioID;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "SENHA_HASH", nullable = false)
    private String senhaHash;

    @Convert(converter = PerfilUsuarioConverter.class)
    @Column(name = "PERFIL", nullable = false)
    private PerfilUsuario perfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EstoqueMovimento> movimentos;
}

