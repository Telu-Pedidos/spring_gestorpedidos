package com.api.gestor_pedidos_telu.dto;

import com.api.gestor_pedidos_telu.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @NotBlank(message = "O login não pode ser vazio")
        @Email(message = "O login deve ser um e-mail válido")
        String login,

        @Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
        String password,

        UserRole role
) {
}