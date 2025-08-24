package com.ml.gestao.estoque.distribuido.util;

import com.ml.gestao.estoque.distribuido.filtro.CampoOrdenacao;
import lombok.Getter;

@Getter
public enum MensagensValidacao {

    ERRO_PRODUTO_OBRIGATORIO("Obrigatório informar os dados do produto."),
    ERRO_PRODUTO_NAO_ENCONTRADO("Produto não encontrado."),
    ERRO_PRODUTO_JA_EXISTE("Produto já cadastrado."),
    ERRO_NOME_PRODUTO_OBRIGATORIO("O nome do produto é obrigatório."),
    ERRO_ESTOQUE_PRODUTO_OBRIGATORIO("O estoque inicial do produto é obrigatório."),
    ERRO_PRECO_PRODUTO_INVALIDO("O preço do produto é invalido."),
    ERRO_SKU_PRODUTO_OBRIGATORIO("SKU do produto é obrigatório."),
    ERRO_SKU_PRODUTO_JA_CADASTRADA("SKU do produto já cadastrada."),
    ERRO_DESCRICAO_PRODUTO_OBRIGATORIO("Descricao do produto é obrigatório."),

    ERRO_TIPO_MOVIMENTO_OBRIGATORIO("Obrigatório informar o tipo da movimentação (ENTRADA ou SAIDA)."),
    ERRO_VALIDACAO_COD_MOVIMENTACAO_NAO_ENCONTRADO("Movimentação não foi encontrada."),
    ERRO_ESTOQUE_JA_EXISTE("Estoque já cadastrado."),
    ERRO_QUANTIDADE_ESTOQUE_INVALIDA("A quantidade de estoque deve ser maior ou igual a zero."),
    ERRO_PRODUTO_ESTOQUE_OBRIGATORIO("O produto vinculado ao estoque é obrigatório."),
    ERRO_LOJA_ESTOQUE_OBRIGATORIO("A loja vinculada ao estoque é obrigatória."),
    ERRO_ESTOQUE_INSUFICIENTE("A quantidade do rpoduto no estoque é insuficiente para a transação."),

    ERRO_LOJA_OBRIGATORIA("Obrigatório informar os dados da loja."),
    ERRO_LOJA_NAO_ENCONTRADA("Loja não encontrada."),
    ERRO_LOJA_JA_EXISTE("Loja já cadastrada."),
    ERRO_NOME_LOJA_OBRIGATORIO("O nome da loja é obrigatório."),
    ERRO_ENDERECO_LOJA_OBRIGATORIO("O endereço da loja é obrigatório."),

    ERRO_USUARIO_JA_EXISTE("Usuário já cadastrado."),
    ERRO_USUARIO_NAO_ENCONTRADO("Usuário não encontrado."),
    ERRO_NOME_USUARIO_OBRIGATORIO("O Nome do Usuário é obrigatório."),
    ERRO_EMAIL_USUARIO_OBRIGATORIO("O e-mail do usuário é obrigatório."),
    ERRO_SENHA_USUARIO_OBRIGATORIO("A senha do usuário é obrigatório."),
    ERRO_PERFIL_USUARIO_OBRIGATORIO("O perfildo usuário é obrigatório.");



    private final String valor;

    MensagensValidacao(String valor) {
        this.valor = valor;
    }

    public static MensagensValidacao findByCodigo(String valor) {
        for (MensagensValidacao value : MensagensValidacao.values()) {
            if (value.getValor().equalsIgnoreCase(valor.trim())) {
                return value;
            }
        }
        return null;
    }

}
