package com.api.gestor_pedidos_telu.domain.client;

import com.api.gestor_pedidos_telu.dto.ClientDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.api.gestor_pedidos_telu.utils.Regex.PHONE_PATTERN;

@Entity(name = "clients")
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caracteres")
    private String name;

    @Email(message = "Deve ser um email válido")
    @Column(unique = true)
    private String email;

    @Size(min = 2, max = 250, message = "O endereço deve ter entre 2 a 250 caracteres")
    private String address;

    @Pattern(
            regexp = PHONE_PATTERN,
            message = "O telefone deve ser válido, com ou sem DDD, e no formato (XX) XXXXX-XXXX ou similar"
    )
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Client(ClientDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.address = data.address();
        this.phone = data.phone();
    }
}