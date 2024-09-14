package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.category.Category;
import com.api.gestor_pedidos_telu.dto.CategoryDTO;
import com.api.gestor_pedidos_telu.infra.Exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return findCategoryByIdOrThrow(id);
    }

    public Category createCategory(CategoryDTO data) {
        Category newCategory = new Category(data);

        validateCategoryNameUniqueness(data.name(), null);

        if (data.slug() == null || data.slug().isEmpty()) {
            newCategory.setSlug(generateSlug(newCategory.getName()));
        }

        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(Long id, CategoryDTO data) {
        Category category = findCategoryByIdOrThrow(id);

        validateCategoryNameUniqueness(data.name(), id);

        BeanUtils.copyProperties(data, category);

        if (data.slug() == null || data.slug().isEmpty()) {
            category.setSlug(generateSlug(category.getName()));
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        findCategoryByIdOrThrow(id);
        categoryRepository.deleteById(id);
    }

    private String generateSlug(String name) {
        if (name != null && !name.trim().isEmpty()) {
            String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD);
            String slug = normalized.replaceAll("\\p{M}", "");

            return slug.toLowerCase().replaceAll(" ", "-").replaceAll("-+", "-");
        }
        return "";
    }

    private void validateCategoryNameUniqueness(String name, Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);

        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(categoryId)) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome");
        }
    }

    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

}
