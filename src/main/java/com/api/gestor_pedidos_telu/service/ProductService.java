package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.category.Category;
import com.api.gestor_pedidos_telu.domain.product.Product;
import com.api.gestor_pedidos_telu.dto.ProductDTO;
import com.api.gestor_pedidos_telu.infra.exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.CategoryRepository;
import com.api.gestor_pedidos_telu.repository.ProductRepository;
import com.api.gestor_pedidos_telu.utils.Prices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.api.gestor_pedidos_telu.utils.Prices.formatPrice;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean active) {
        if (name != null && minPrice != null && maxPrice != null && active != null) {
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetweenAndActive(name.toLowerCase(), minPrice, maxPrice, active)
                    .orElse(Collections.emptyList());
        }

        if (name != null && active != null) {
            return productRepository.findByNameContainingIgnoreCaseAndActive(name.toLowerCase(), active)
                    .orElse(Collections.emptyList());
        }

        if (name != null) {
            return productRepository.findByNameContainingIgnoreCase(name)
                    .orElse(Collections.emptyList());
        }

        if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice)
                    .orElse(Collections.emptyList());
        }

        if (minPrice != null) {
            return productRepository.findByPriceGreaterThanEqual(minPrice)
                    .orElse(Collections.emptyList());
        }

        if (maxPrice != null) {
            return productRepository.findByPriceLessThanEqual(maxPrice)
                    .orElse(Collections.emptyList());
        }

        if (active != null) {
            return productRepository.findAllByActive(active).orElse(Collections.emptyList());
        }

        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return findProductByIdOrThrow(id);
    }

    public Product createProduct(ProductDTO data) {
        Category category = findCategoryByIdOrThrow(data.categoryId());

        validateProductNameUniqueness(data.name(), null);

        Product newProduct = new Product(data, category);

        if (data.slug() == null || data.slug().isEmpty()) {
            newProduct.setSlug(generateSlug(newProduct.getName()));
        }

        BigDecimal formattedPrice = formatPrice(newProduct.getPrice());
        newProduct.setPrice(formattedPrice);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductDTO data) {
        Product product = findProductByIdOrThrow(id);
        validateProductNameUniqueness(data.name(), id);

        Category category = findCategoryByIdOrThrow(data.categoryId());
        BeanUtils.copyProperties(data, product);
        product.setCategory(category);

        if (data.slug() == null || data.slug().isEmpty()) {
            product.setSlug(generateSlug(product.getName()));
        }

        BigDecimal formattedPrice = formatPrice(product.getPrice());
        product.setPrice(formattedPrice);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        findProductByIdOrThrow(id);
        productRepository.deleteById(id);
    }

    public Product activateProduct(Long id) throws Exception {
        Product product = findProductByIdOrThrow(id);

        if (product.getActive()) throw new Exception("Produto já está ativo");

        product.setActive(true);
        return productRepository.save(product);
    }

    public Product deactivateProduct(Long id) throws Exception {
        Product product = findProductByIdOrThrow(id);

        if (!product.getActive()) throw new Exception("Produto já está desativo");

        product.setActive(false);
        return productRepository.save(product);
    }

    private String generateSlug(String name) {
        if (name != null && !name.trim().isEmpty()) {
            String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD);
            String slug = normalized.replaceAll("\\p{M}", "");

            return slug.toLowerCase().replaceAll(" ", "-").replaceAll("-+", "-");
        }
        return "";
    }

    private void validateProductNameUniqueness(String name, Long productId) {
        Optional<Product> existingProduct = productRepository.findByName(name);

        if (existingProduct.isPresent() && !existingProduct.get().getId().equals(productId)) {
            throw new IllegalArgumentException("Já existe um produto com esse nome");
        }
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    private Category findCategoryByIdOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

}
