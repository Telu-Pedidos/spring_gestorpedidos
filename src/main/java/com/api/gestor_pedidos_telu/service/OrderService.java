package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.client.Client;
import com.api.gestor_pedidos_telu.domain.order.Order;
import com.api.gestor_pedidos_telu.domain.order.OrderStatus;
import com.api.gestor_pedidos_telu.domain.product.Product;
import com.api.gestor_pedidos_telu.infra.exception.InvalidOrderDateException;
import com.api.gestor_pedidos_telu.infra.exception.NotFoundException;
import com.api.gestor_pedidos_telu.dto.OrderDTO;
import com.api.gestor_pedidos_telu.repository.ClientRepository;
import com.api.gestor_pedidos_telu.repository.OrderRepository;
import com.api.gestor_pedidos_telu.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static com.api.gestor_pedidos_telu.utils.Prices.calculateTotal;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getOrders(OrderStatus status, ZonedDateTime startDate, ZonedDateTime endDate) {
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

    public Order getOrderById(Long orderId) {
        return findOrderByIdOrThrow(orderId);
    }

    public Order createOrder(OrderDTO order) {
        Client client = findClientByIdOrThrow(order.clientId());

        List<Product> products = productRepository.findAllById(order.productIds());

        validateOrderDates(order);

        BigDecimal newTotal = calculateTotal(products);

        Order newOrder = new Order(order, client, products);

        if (order.total() == null) newOrder.setTotal(newTotal);
        if (order.startAt() == null) newOrder.setStartAt(ZonedDateTime.now());
        if (order.status() == null) newOrder.setStatus(OrderStatus.PENDING);

        return orderRepository.save(newOrder);
    }

    public Order updateOrder(Long orderId, OrderDTO order) {
        Order newOrder = findOrderByIdOrThrow(orderId);

        validateOrderDates(order);

        Client client = findClientByIdOrThrow(order.clientId());
        List<Product> products = productRepository.findAllById(order.productIds());

        BigDecimal newTotal = calculateTotal(products);

        BeanUtils.copyProperties(order, newOrder);
        newOrder.setClient(client);
        newOrder.setProducts(products);

        if (order.total() == null) newOrder.setTotal(newTotal);

        return orderRepository.save(newOrder);
    }

    public void deleteOrder(Long orderId) {
        findOrderByIdOrThrow(orderId);
        orderRepository.deleteById(orderId);
    }

    public Order cancelOrder(Long orderId) throws Exception {
        Order order = findOrderByIdOrThrow(orderId);

        if (order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setEndAt(ZonedDateTime.now());
            order.setUpdatedAt(ZonedDateTime.now());
            return orderRepository.save(order);
        } else {
            throw new Exception("Não é possível cancelar um pedido finalizado ou já cancelado.");
        }
    }

    public Order finishOrder(Long orderId) throws Exception {
        Order order = findOrderByIdOrThrow(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new Exception("Não é possível finalizar um pedido cancelado");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new Exception("Não é possível finalizar um pedido que já está finalizado");
        }

        order.setStatus(OrderStatus.COMPLETED);
        order.setEndAt(ZonedDateTime.now());
        order.setUpdatedAt(ZonedDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = findOrderByIdOrThrow(orderId);
        order.setStatus(newStatus);
        order.setUpdatedAt(ZonedDateTime.now());
        return orderRepository.save(order);
    }

    private Order findOrderByIdOrThrow(Long orderId) {
        return orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }

    private Client findClientByIdOrThrow(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    private void validateOrderDates(OrderDTO order) {
        ZonedDateTime now = ZonedDateTime.now();

        if (order.startAt().isAfter(now)) {
            throw new InvalidOrderDateException("A datas de inicio deve ser menor do que a data atual.");
        }

        if (order.endAt().isBefore(order.startAt())) {
            throw new InvalidOrderDateException("A data final não pode ser anterior à data de início.");
        }
    }

}