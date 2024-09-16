package com.api.gestor_pedidos_telu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
        String name,

        @Size(max = 150, message = "O slug deve ter no máximo 150 caracteres")
        String slug,

        @Size(max = 255, message = "O modelo deve ter no máximo 255 caracteres")
        String model
) {
}
