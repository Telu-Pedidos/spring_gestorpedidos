package com.api.gestor_pedidos_telu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.api.gestor_pedidos_telu.utils.Regex.PHONE_PATTERN;

public record ClientDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caracteres")
        String name,

        @Email(message = "Deve ser um email válido")
        String email,

        @Size(max = 250, message = "O endereço não pode passar de 250 caracteres")
        String address,

        @Column(unique = true)
        @Pattern(
                regexp = PHONE_PATTERN,
                message = "O telefone deve ser válido, com ou sem DDD, e no formato (XX) XXXXX-XXXX ou similar"
        )
        String phone
) {
}
