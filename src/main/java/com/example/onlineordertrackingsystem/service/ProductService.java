package com.example.onlineordertrackingsystem.service;

import com.example.onlineordertrackingsystem.dto.ProductDto;
import com.example.onlineordertrackingsystem.model.Category;
import com.example.onlineordertrackingsystem.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductByUuid(String uuid);

    String saveProduct(ProductDto ProductDto);

    String updateProduct(String uuid, ProductDto updatedProductDto);

    String deleteProduct(String uuid);

    List<ProductDto> filterProductsByCategory(Category category);

    List<ProductDto> searchProductsByName(String keyword);

    List<ProductDto> searchProductsByPriceRange(double minPrice, double maxPrice);

    List<ProductDto> getProductsByPrice(double price);

    List<ProductDto> getProductsByUser(String userUuid);

    void updateStockQuantity(String productUuid, int newQuantity);

    void returnProduct(String productUuid, int quantityReturned);

    void sellProduct(String productUuid, int quantitySold);

    List<ProductDto> filterProductsByCategoryAndPriceRange(String categoryUuid, double minPrice, double maxPrice);

    List<ProductDto> searchProductsByCategoryAndName(String categoryUuid, String keyword);

    List<ProductDto> getProductsByCategory(String categoryUuid);

    Product convertToEntity(ProductDto productDto);
}
