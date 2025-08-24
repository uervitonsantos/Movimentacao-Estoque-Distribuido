package com.ml.gestao.estoque.distribuido.util.adapter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> implements ParamConverter<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String string) throws Exception {
        return LocalDate.parse(string, FORMATTER);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(FORMATTER);
    }

    @Override
    public LocalDate fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalDate.parse(value, FORMATTER);
    }

    @Override
    public String toString(LocalDate localDate) {
        return localDate != null ? localDate.format(FORMATTER) : null;
    }
}
