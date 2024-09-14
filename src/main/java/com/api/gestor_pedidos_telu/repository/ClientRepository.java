package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findById(String id);

    Optional<Client> findByEmail(String email);

    Optional<List<Client>> findByNameContainingIgnoreCase(String name);

}
