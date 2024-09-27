package com.api.gestor_pedidos_telu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModelDTO(
        
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 a 255 caracteres")
        String name,

        @Size(max = 2000)
        String imageUrl
) {
}
