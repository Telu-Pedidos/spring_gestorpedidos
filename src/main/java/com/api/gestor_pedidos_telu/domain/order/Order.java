package com.api.gestor_pedidos_telu.domain.order;

import com.api.gestor_pedidos_telu.dto.OrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "orders")
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull(message = "O total é obrigatório")
    @Positive(message = "O total deve ser maior que zero.")
    private BigDecimal total;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime startAt;

    @NotNull(message = "A data de término é obrigatória")
    @NotNull()
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Order(OrderDTO data) {
        this.startAt = data.startAt();
        this.endAt = data.endAt();
        this.status = data.status();
        this.total = data.total();
    }

}