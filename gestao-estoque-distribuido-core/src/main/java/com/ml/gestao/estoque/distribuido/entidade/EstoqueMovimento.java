package com.ml.gestao.estoque.distribuido.entidade;

import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "ESTOQUE_MOVIMENTO")
public class EstoqueMovimento implements Serializable {

    @Id
    @Column(name = "MOVIMENTO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimento_sequence")
    @SequenceGenerator(name = "movimento_sequence", sequenceName = "SEQ_MOVIMENTO", allocationSize = 1)
    private Long movimentoID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUTO_ID", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOJA_ID")
    private Loja loja;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MOVIMENTO", nullable = false)
    private TipoMovimento tipoMovimento;

    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantidade;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
}
