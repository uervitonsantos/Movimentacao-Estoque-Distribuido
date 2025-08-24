package com.ml.gestao.estoque.distribuido.util.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.math.BigDecimal;

public class BigDecimalAdapter extends XmlAdapter<BigDecimal, Double> {

    public Double unmarshal(BigDecimal v) throws Exception {
        return v != null ? v.doubleValue() : null;
    }

    public BigDecimal marshal(Double value) throws Exception {
        if (value == null) {
            return null;
        }
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
