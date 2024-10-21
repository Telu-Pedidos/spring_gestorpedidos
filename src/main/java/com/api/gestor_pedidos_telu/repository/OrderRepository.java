package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.order.Order;
import com.api.gestor_pedidos_telu.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);

    Optional<List<Order>> findAllByStatus(OrderStatus status);

    Optional<List<Order>> findByStartAtBetween(ZonedDateTime startDate, ZonedDateTime endDate);

    Optional<List<Order>> findByStatusAndStartAtBetween(OrderStatus status, ZonedDateTime startAt, ZonedDateTime endAt);

    @Query("SELECT o FROM orders o WHERE MONTH(o.startAt) = :month")
    Optional<List<Order>> findByMonth(@Param("month") int month);
}