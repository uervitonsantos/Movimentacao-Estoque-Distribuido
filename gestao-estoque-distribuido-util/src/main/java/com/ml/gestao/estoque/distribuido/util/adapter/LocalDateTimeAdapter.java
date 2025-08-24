package com.ml.gestao.estoque.distribuido.util.adapter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> implements ParamConverter<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String string) throws Exception {
        return LocalDateTime.parse(string, FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.format(FORMATTER);
    }

    @Override
    public LocalDateTime fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(value, FORMATTER);
    }

    @Override
    public String toString(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(FORMATTER) : null;
    }
}
