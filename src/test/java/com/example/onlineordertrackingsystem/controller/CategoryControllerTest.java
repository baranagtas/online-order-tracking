package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.CategoryDto;
import com.example.onlineordertrackingsystem.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void addCategoryToProduct() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");

        when(categoryService.addCategoryToProduct(any(CategoryDto.class), anyString())).thenReturn(categoryDto);

        mockMvc.perform(post("/add-to-product/{productUuid}", "productUuid")
                        .content(new ObjectMapper().writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    void editCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Edited Category");

        when(categoryService.editCategory(anyString(), any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(put("/edit/{uuid}", "uuid")
                        .content(new ObjectMapper().writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Edited Category"));
    }

    @Test
    void findCategoryByUuid() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");

        when(categoryService.findCategoryByUuid(anyString())).thenReturn(categoryDto);

        mockMvc.perform(get("/{uuid}", "uuid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    void deleteCategory() throws Exception {
        String message = "Category deleted successfully";

        when(categoryService.deleteCategory(anyString())).thenReturn(message);

        mockMvc.perform(delete("/delete/{uuid}", "uuid"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
    }

    @Test
    void findAllCategories() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");

        when(categoryService.findAllCategories()).thenReturn(Collections.singletonList(categoryDto));

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Category"));
    }

    @Test
    void findCategoriesForProduct() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");

        when(categoryService.findCategoriesForProduct(anyString())).thenReturn(Collections.singletonList(categoryDto));

        mockMvc.perform(get("/for-product/{productUuid}", "productUuid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Category"));
    }
}
