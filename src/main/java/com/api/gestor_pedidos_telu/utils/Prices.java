package com.api.gestor_pedidos_telu.utils;

import com.api.gestor_pedidos_telu.domain.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class Prices {
    public static BigDecimal calculateTotal(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
