package com.api.gestor_pedidos_telu.domain.order;

import com.api.gestor_pedidos_telu.domain.client.Client;
import com.api.gestor_pedidos_telu.domain.product.Product;
import com.api.gestor_pedidos_telu.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity(name = "orders")
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Positive(message = "O total deve ser maior que zero.")
    private BigDecimal total;

    private ZonedDateTime startAt;

    private ZonedDateTime endAt;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private OrderDelivery delivery;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Client client;

    @Size(max = 2000, message = "A observação deve ter no máximo 2000 caracteres")
    private String observation;

    @ManyToMany
    @JsonIgnoreProperties("products")
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Order(OrderDTO data, Client client, List<Product> products) {
        this.startAt = data.startAt();
        this.endAt = data.endAt();
        this.status = data.status();
        this.total = data.total();
        this.client = client;
        this.products = products;
        this.delivery = data.delivery();
        this.observation = data.observation();
    }

}