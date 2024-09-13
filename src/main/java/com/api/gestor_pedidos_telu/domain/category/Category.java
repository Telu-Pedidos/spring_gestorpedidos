package com.api.gestor_pedidos_telu.domain.category;

import com.api.gestor_pedidos_telu.dto.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "categories")
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 a 100 caracteres")
    private String name;

    @Size(max = 150, message = "O slug deve ter no máximo 150 caracteres")
    private String slug;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(max = 255, message = "O modelo deve ter no máximo 255 caracteres")
    private String model;

    public Category(CategoryDTO data) {
        this.name = data.name();
        this.slug = data.slug();
        this.model = data.model();
    }
}
