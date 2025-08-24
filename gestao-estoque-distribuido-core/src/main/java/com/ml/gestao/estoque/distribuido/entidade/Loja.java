package com.ml.gestao.estoque.distribuido.entidade;

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
@Table(name = "LOJA")
public class Loja implements Serializable {

    @Id
    @Column(name = "LOJA_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loja_sequence")
    @SequenceGenerator(name = "loja_sequence", sequenceName = "SEQ_LOJA", allocationSize = 1)
    private Long lojaID;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loja", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EstoqueMovimento> movimentos;
}
