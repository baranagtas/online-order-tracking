package com.example.onlineordertrackingsystem.service.impl;

import com.example.onlineordertrackingsystem.dto.CategoryDto;
import com.example.onlineordertrackingsystem.model.Category;
import com.example.onlineordertrackingsystem.model.Product;
import com.example.onlineordertrackingsystem.repository.CategoryRepository;
import com.example.onlineordertrackingsystem.repository.ProductRepository;
import com.example.onlineordertrackingsystem.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

    }

    @Override
    public CategoryDto addCategoryToProduct(CategoryDto categoryDto, String productUuid) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new RuntimeException("Product not found with UUID: " + productUuid));

        Category category = convertToEntity(categoryDto);
        category.getProductList().add(product);

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);

    }

    @Override
    public CategoryDto editCategory(String uuid, CategoryDto categoryDto) {
        Category categoryToUpdate = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Category not found with UUID: " + uuid));

        categoryToUpdate.setName(categoryDto.getName());
        categoryToUpdate.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return convertToDto(updatedCategory);
    }

    @Override
    public CategoryDto findCategoryByUuid(String uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Category not found with UUID: " + uuid));
        return convertToDto(category);
    }

    @Override
    public String deleteCategory(String uuid) {
        Category categoryToDelete = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Category not found with UUID: " + uuid));

        categoryRepository.delete(categoryToDelete);
        return "Category deleted successfully";
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findCategoriesForProduct(String productUuid) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new RuntimeException("Product not found with UUID: " + productUuid));

        CategoryDto categoryDto = convertToDto(product.getCategory());
        return Collections.singletonList(categoryDto);
    }

    private CategoryDto convertToDto(Category category) {
        // Entity'den Dto'ya dönüştürme işlemi
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        // Entity'den Dto'ya dönüştürme işlemi
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        return category;
    }
}
