package com.ml.gestao.estoque.distribuido.entidade;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "PRODUTO")
public class Produto implements Serializable {

    @Id
    @Column(name = "PRODUTO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_sequence")
    @SequenceGenerator(name = "produto_sequence", sequenceName = "SEQ_PRODUTO", allocationSize = 1)
    private Long produtoID;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "SKU", unique = true, nullable = false)
    private String sku;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "PRECO", precision = 15, scale = 2)
    private BigDecimal preco;

    @Column(name = "ESTOQUE_ATUAL")
    private Integer estoqueAtual;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE})
    private List<EstoqueMovimento> movimentos;
}
