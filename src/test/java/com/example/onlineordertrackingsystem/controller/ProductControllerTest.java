package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.ProductDto;
import com.example.onlineordertrackingsystem.service.ProductService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDto> productList = new ArrayList<>();
        // productList'i doldurun

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductByUuid() throws Exception {
        String uuid = "exampleUuid";
        ProductDto productDto = new ProductDto();
        // productDto'yu doldurun

        when(productService.getProductByUuid(uuid)).thenReturn(productDto);

        mockMvc.perform(get("/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void testSaveProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        // productDto'yu doldurun

        when(productService.saveProduct(any(ProductDto.class))).thenReturn("Product saved successfully");

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product saved successfully"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        String uuid = "exampleUuid";
        ProductDto updatedProductDto = new ProductDto();
        // updatedProductDto'yu doldurun

        when(productService.updateProduct(eq(uuid), any(ProductDto.class))).thenReturn("Product updated successfully");

        mockMvc.perform(put("/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProductDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product updated successfully"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        String uuid = "exampleUuid";

        when(productService.deleteProduct(uuid)).thenReturn("Product deleted successfully");

        mockMvc.perform(delete("/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    @Test
    void testGetProductsByPrice() throws Exception {
        double price = 100.0;
        List<ProductDto> filteredProducts = new ArrayList<>();
        // filteredProducts'ü doldurun

        when(productService.getProductsByPrice(price)).thenReturn(filteredProducts);

        mockMvc.perform(get("/filter-by-price/{price}", price))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testReturnProduct() throws Exception {
        String productUuid = "exampleProductUuid";
        int quantityReturned = 1;

        doNothing().when(productService).returnProduct(productUuid, quantityReturned);

        mockMvc.perform(post("/return-product")
                        .param("productUuid", productUuid)
                        .param("quantityReturned", String.valueOf(quantityReturned)))
                .andExpect(status().isOk());
    }

    @Test
    void testSellProduct() throws Exception {
        String productUuid = "exampleProductUuid";
        int quantitySold = 1;

        doNothing().when(productService).sellProduct(productUuid, quantitySold);

        mockMvc.perform(post("/sell-product")
                        .param("productUuid", productUuid)
                        .param("quantitySold", String.valueOf(quantitySold)))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterProductsByCategoryAndPriceRange() throws Exception {
        String categoryUuid = "exampleCategoryUuid";
        double minPrice = 50.0;
        double maxPrice = 100.0;
        List<ProductDto> filteredProducts = new ArrayList<>();
        // filteredProducts'ü doldurun

        when(productService.filterProductsByCategoryAndPriceRange(categoryUuid, minPrice, maxPrice)).thenReturn(filteredProducts);

        mockMvc.perform(get("/filter-by-category-and-price-range")
                        .param("categoryUuid", categoryUuid)
                        .param("minPrice", String.valueOf(minPrice))
                        .param("maxPrice", String.valueOf(maxPrice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testSearchProductsByCategoryAndName() throws Exception {
        String categoryUuid = "exampleCategoryUuid";
        String keyword = "exampleKeyword";
        List<ProductDto> filteredProducts = new ArrayList<>();
        // filteredProducts'ü doldurun

        when(productService.searchProductsByCategoryAndName(categoryUuid, keyword)).thenReturn(filteredProducts);

        mockMvc.perform(get("/search-by-category-and-name")
                        .param("categoryUuid", categoryUuid)
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        String categoryUuid = "exampleCategoryUuid";
        List<ProductDto> filteredProducts = new ArrayList<>();
        // filteredProducts'ü doldurun

        when(productService.getProductsByCategory(categoryUuid)).thenReturn(filteredProducts);

        mockMvc.perform(get("/get-by-category/{categoryUuid}", categoryUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testSearchProductsByName() throws Exception {
        String keyword = "exampleKeyword";
        List<ProductDto> products = new ArrayList<>();
        // products'ü doldurun

        when(productService.searchProductsByName(keyword)).thenReturn(products);

        mockMvc.perform(get("/search")
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testSearchProductsByPriceRange() throws Exception {
        double minPrice = 50.0;
        double maxPrice = 100.0;
        List<ProductDto> products = new ArrayList<>();
        // products'ü doldurun

        when(productService.searchProductsByPriceRange(minPrice, maxPrice)).thenReturn(products);

        mockMvc.perform(get("/price-range")
                        .param("minPrice", String.valueOf(minPrice))
                        .param("maxPrice", String.valueOf(maxPrice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductsByUser() throws Exception {
        String userUuid = "exampleUserUuid";
        List<ProductDto> products = new ArrayList<>();
        // products'ü doldurun

        when(productService.getProductsByUser(userUuid)).thenReturn(products);

        mockMvc.perform(get("/user/{userUuid}", userUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // Nesneleri JSON formatına dönüştürmek için yardımcı metod
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
