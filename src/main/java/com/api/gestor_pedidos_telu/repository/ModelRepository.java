package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findById(Long id);

    Optional<Model> findByName(String name);
}
