package com.example.onlineordertrackingsystem.service;

import com.example.onlineordertrackingsystem.dto.CategoryDto;
import com.example.onlineordertrackingsystem.model.Category;

import java.util.List;

public interface CategoryService {

    // Yeni bir kategori ekler ve verilen ürüne bağlar
    CategoryDto addCategoryToProduct(CategoryDto categoryDto, String productUuid);

    // Kategoriyi günceller
    CategoryDto editCategory(String uuid, CategoryDto categoryDto);

    // UUID'ye göre kategori bulur
    CategoryDto findCategoryByUuid(String uuid);

    // ID'ye göre kategori siler
    String deleteCategory(String uuid);

    // Tüm kategorileri listeler
    List<CategoryDto> findAllCategories();

    // Belirli bir ürün için kategorileri listeler
    List<CategoryDto> findCategoriesForProduct(String productUuid);

}
