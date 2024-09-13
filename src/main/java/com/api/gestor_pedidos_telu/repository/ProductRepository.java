package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<List<Product>> findByNameContainingIgnoreCase(String name);

    Optional<List<Product>> findByNameContainingIgnoreCaseAndActive(String name, Boolean active);

    Optional<List<Product>> findByNameContainingIgnoreCaseAndPriceBetweenAndActive(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean active);

    Optional<List<Product>> findAllByActive(Boolean active);

    Optional<List<Product>> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    Optional<List<Product>> findByPriceGreaterThanEqual(BigDecimal minPrice);

    Optional<List<Product>> findByPriceLessThanEqual(BigDecimal maxPrice);
}
