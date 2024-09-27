package com.api.gestor_pedidos_telu.domain.model;

import com.api.gestor_pedidos_telu.domain.product.Product;
import com.api.gestor_pedidos_telu.dto.ModelDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity(name = "models")
@Table(name = "models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 a 255 caracteres")
    private String name;

    @Size(max = 2000)
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("model")
    private List<Product> products;

    public Model(ModelDTO data) {
        this.name = data.name();
        this.imageUrl = data.imageUrl();
    }
}
