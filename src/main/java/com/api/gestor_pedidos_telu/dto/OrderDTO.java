package com.api.gestor_pedidos_telu.dto;

import com.api.gestor_pedidos_telu.domain.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        LocalDateTime startAt,
        LocalDateTime endAt,
        OrderStatus status,
        BigDecimal total
) {

}