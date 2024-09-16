package com.api.gestor_pedidos_telu.dto;

import com.api.gestor_pedidos_telu.domain.order.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        @Enumerated(EnumType.STRING)
        OrderStatus status,

        @Positive(message = "O total deve ser maior que zero.")
        BigDecimal total,

        @NotNull(message = "Deve ter uma data de início")
        LocalDateTime startAt,

        @NotNull(message = "Deve ter uma data final")
        LocalDateTime endAt,

        @NotNull(message = "O cliente é obrigatório")
        String clientId,

        @NotNull(message = "Os produtos são obrigatórios")
        List<Long> productIds
) {

}