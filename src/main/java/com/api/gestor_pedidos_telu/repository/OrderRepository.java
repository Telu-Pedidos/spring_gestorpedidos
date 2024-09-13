package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.order.Order;
import com.api.gestor_pedidos_telu.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);

    Optional<List<Order>> findAllByStatus(OrderStatus status);

    Optional<List<Order>> findByStartAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<List<Order>> findByStatusAndStartAtBetween(OrderStatus status, LocalDateTime startAt, LocalDateTime endAt);

}