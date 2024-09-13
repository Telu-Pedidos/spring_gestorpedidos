package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

}
