package com.api.gestor_pedidos_telu.domain.product;

import com.api.gestor_pedidos_telu.domain.category.Category;
import com.api.gestor_pedidos_telu.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 a 255 caracteres")
    private String name;

    @Size(max = 500, message = "O slug deve ter no máximo 500 caracteres")
    private String slug;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(max = 2000)
    private String imageUrl;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "Deve ser um preço válido")
    private BigDecimal price;

    private Boolean active = true;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    public Product(ProductDTO data, Category category) {
        this.name = data.name();
        this.slug = data.slug();
        this.imageUrl = data.imageUrl();
        this.price = data.price();
        this.category = category;
    }
}
