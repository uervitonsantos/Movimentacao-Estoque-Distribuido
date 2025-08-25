package com.ml.gestao.estoque.distribuido.converter;


import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class PerfilUsuarioConverter implements AttributeConverter<PerfilUsuario, String> {

    @Override
    public String convertToDatabaseColumn(PerfilUsuario perfilUsuario) {
        return Optional.ofNullable(perfilUsuario).map(a -> perfilUsuario.getValor()).orElse(null);
    }

    @Override
    public PerfilUsuario convertToEntityAttribute(String string) {
        return PerfilUsuario.findByCodigo(string);
    }
}
