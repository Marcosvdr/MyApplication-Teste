package com.example.myapplication.ui.enums;

import java.util.Arrays;
import java.util.List;

public enum ProductStatus {
    N("Normal"),
    E("Em estoque"),
    L("Lançamento"),
    P("Promoção");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<ProductStatus> getAllStatus() {
        return Arrays.asList(values());
    }
}
