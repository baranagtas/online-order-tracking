package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.CategoryDto;
import com.example.onlineordertrackingsystem.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-to-product/{productUuid}")
    public ResponseEntity<CategoryDto> addCategoryToProduct(@RequestBody CategoryDto categoryDto, @PathVariable String productUuid) {
        CategoryDto addedCategory = categoryService.addCategoryToProduct(categoryDto, productUuid);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{uuid}")
    public ResponseEntity<CategoryDto> editCategory(@PathVariable String uuid, @RequestBody CategoryDto categoryDto) {
        CategoryDto editedCategory = categoryService.editCategory(uuid, categoryDto);
        return ResponseEntity.ok(editedCategory);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryDto> findCategoryByUuid(@PathVariable String uuid) {
        CategoryDto category = categoryService.findCategoryByUuid(uuid);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteCategory(@PathVariable String uuid) {
        String message = categoryService.deleteCategory(uuid);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAllCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/for-product/{productUuid}")
    public ResponseEntity<List<CategoryDto>> findCategoriesForProduct(@PathVariable String productUuid) {
        List<CategoryDto> categories = categoryService.findCategoriesForProduct(productUuid);
        return ResponseEntity.ok(categories);
    }
}