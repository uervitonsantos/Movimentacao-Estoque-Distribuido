package com.ml.gestao.estoque.distribuido.converter;


import com.ml.gestao.estoque.distribuido.util.MensagensValidacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class RecepcaoPedidoMensagensConverter implements AttributeConverter<MensagensValidacao, String> {
    @Override
    public String convertToDatabaseColumn(MensagensValidacao mensagensValidacao) {
        return Optional.ofNullable(mensagensValidacao).map(a -> mensagensValidacao.getValor()).orElse(null);
    }

    @Override
    public MensagensValidacao convertToEntityAttribute(String string) {
        return MensagensValidacao.findByCodigo(string);
    }
}
