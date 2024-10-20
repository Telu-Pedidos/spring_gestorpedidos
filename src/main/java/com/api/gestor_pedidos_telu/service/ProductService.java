package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.category.Category;
import com.api.gestor_pedidos_telu.domain.model.Model;
import com.api.gestor_pedidos_telu.domain.product.Product;
import com.api.gestor_pedidos_telu.dto.ProductDTO;
import com.api.gestor_pedidos_telu.infra.exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.CategoryRepository;
import com.api.gestor_pedidos_telu.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.api.gestor_pedidos_telu.utils.Prices.formatPrice;
import static com.api.gestor_pedidos_telu.utils.Slug.generateSlug;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private CategoryService categoryService;

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
        Product newProduct = new Product(data);

        if (data.slug() == null || data.slug().isEmpty()) {
            newProduct.setSlug(generateSlug(newProduct.getName()));
        }

        if (data.modelId() != null) {
            Model model = modelService.getModelById(data.modelId());
            newProduct.setModel(model);
        }

        if (data.categoryId() != null) {
            Category category = categoryService.getCategoryById(data.categoryId());
            newProduct.setCategory(category);
        }

        BigDecimal formattedPrice = formatPrice(newProduct.getPrice());
        newProduct.setPrice(formattedPrice);

        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, ProductDTO data) {
        Product product = findProductByIdOrThrow(id);

        BeanUtils.copyProperties(data, product);

        if (data.slug() == null || data.slug().isEmpty()) {
            product.setSlug(generateSlug(product.getName()));
        }

        if (data.modelId() != null) {
            Model model = modelService.getModelById(data.modelId());
            product.setModel(model);
        }

        if (data.categoryId() != null) {
            Category category = categoryService.getCategoryById(data.categoryId());
            product.setCategory(category);
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

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

}
