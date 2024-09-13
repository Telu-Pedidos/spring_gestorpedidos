package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.order.Order;
import com.api.gestor_pedidos_telu.domain.order.OrderStatus;
import com.api.gestor_pedidos_telu.infra.Exception.InvalidOrderDateException;
import com.api.gestor_pedidos_telu.infra.Exception.OrderNotFoundException;
import com.api.gestor_pedidos_telu.dto.OrderDTO;
import com.api.gestor_pedidos_telu.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrders(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        if (status != null && startDate != null && endDate != null) {
            return orderRepository.findByStatusAndStartAtBetween(status, startDate, endDate)
                    .orElse(Collections.emptyList());
        }

        if (status != null) {
            return orderRepository.findAllByStatus(status).orElse(Collections.emptyList());
        }

        if (startDate != null && endDate != null) {
            return orderRepository.findByStartAtBetween(startDate, endDate)
                    .orElse(Collections.emptyList());
        }

        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return findOrderByIdOrThrow(id);
    }

    public Order createOrder(OrderDTO data) {
        validateOrderDates(data);

        Order newOrder = new Order(data);
        return orderRepository.save(newOrder);
    }

    public Order updateOrder(Long id, OrderDTO data) {
        Order order = findOrderByIdOrThrow(id);
        validateOrderDates(data);

        BeanUtils.copyProperties(data, order);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        findOrderByIdOrThrow(id);
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status).orElse(Collections.emptyList());
    }

    public Order cancelOrder(Long id) throws Exception {
        Order order = findOrderByIdOrThrow(id);

        if (order.getStatus() != OrderStatus.FINISHED && order.getStatus() != OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setEndAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        } else {
            throw new Exception("Não é possível cancelar um pedido finalizado ou já cancelado.");
        }
    }

    public Order finishOrder(Long id) throws Exception {
        Order order = findOrderByIdOrThrow(id);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new Exception("Não é possível finalizar um pedido cancelado");
        }

        if (order.getStatus() == OrderStatus.FINISHED) {
            throw new Exception("Não é possível finalizar um pedido que já está finalizado");
        }

        order.setStatus(OrderStatus.FINISHED);
        order.setEndAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = findOrderByIdOrThrow(id);
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public BigDecimal calculateTotal(OrderDTO data) {
        // TODO - A IMPLEMENTAR
        return data.total();
    }

    private Order findOrderByIdOrThrow(Long id) {
        return orderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
    }

    private void validateOrderDates(OrderDTO data) {
        LocalDateTime now = LocalDateTime.now();
        if (data.startAt().isAfter(now) || data.endAt().isAfter(now)) {
            throw new InvalidOrderDateException("As datas startAt e endAt devem ser menores do que a data atual.");
        }
        if (data.endAt().isBefore(data.startAt())) {
            throw new InvalidOrderDateException("A data endAt não pode ser anterior à data startAt.");
        }
    }

}