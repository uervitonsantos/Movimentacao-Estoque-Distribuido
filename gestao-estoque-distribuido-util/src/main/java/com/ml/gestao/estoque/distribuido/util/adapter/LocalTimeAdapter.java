package com.ml.gestao.estoque.distribuido.util.adapter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> implements ParamConverter<LocalTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public LocalTime unmarshal(String string) throws Exception {
        return LocalTime.parse(string, FORMATTER);
    }

    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return localTime.format(FORMATTER);
    }

    @Override
    public LocalTime fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalTime.parse(value, FORMATTER);
    }

    @Override
    public String toString(LocalTime localTime) {
        return localTime != null ? localTime.format(FORMATTER) : null;
    }
}
