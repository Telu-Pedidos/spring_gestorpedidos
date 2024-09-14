package com.api.gestor_pedidos_telu.dto;

import java.math.BigDecimal;

public record ProductDTO(
        String name,
        String slug,
        String imageUrl,
        BigDecimal price,
        Long categoryId
) {
}
