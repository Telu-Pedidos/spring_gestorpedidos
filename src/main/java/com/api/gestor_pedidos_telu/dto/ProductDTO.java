package com.api.gestor_pedidos_telu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 a 255 caracteres")
        String name,

        @Size(max = 500, message = "O slug deve ter no máximo 500 caracteres")
        String slug,

        @Size(max = 2000)
        String imageUrl,

        @NotNull(message = "O preço é obrigatório")
        @Min(value = 0, message = "Deve ser um preço válido")
        BigDecimal price,

        @NotNull(message = "A categoria é obrigatória")
        Long categoryId,

        Long modelId
) {
}
