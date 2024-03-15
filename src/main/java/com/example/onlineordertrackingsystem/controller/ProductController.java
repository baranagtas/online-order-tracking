package com.example.onlineordertrackingsystem.controller;

import com.example.onlineordertrackingsystem.dto.ProductDto;
import com.example.onlineordertrackingsystem.model.Category;
import com.example.onlineordertrackingsystem.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDto> getProductByUuid(@PathVariable String uuid) {
        ProductDto productDto = productService.getProductByUuid(uuid);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto) {
        String message = productService.saveProduct(productDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateProduct(@PathVariable String uuid, @RequestBody ProductDto updatedProductDto) {
        String message = productService.updateProduct(uuid, updatedProductDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String uuid) {
        String message = productService.deleteProduct(uuid);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/filter-by-category/{categoryUuid}")
    public ResponseEntity<List<ProductDto>> filterProductsByCategory(@PathVariable String categoryUuid) {
        Category category = new Category();
        category.setUuid(categoryUuid);
        List<ProductDto> filteredProducts = productService.filterProductsByCategory(category);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/filter-by-price/{price}")
    public ResponseEntity<List<ProductDto>> getProductsByPrice(@PathVariable double price) {
        List<ProductDto> filteredProducts = productService.getProductsByPrice(price);
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/return-product")
    public ResponseEntity<Void> returnProduct(@RequestParam String productUuid, @RequestParam int quantityReturned) {
        productService.returnProduct(productUuid, quantityReturned);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell-product")
    public ResponseEntity<Void> sellProduct(@RequestParam String productUuid, @RequestParam int quantitySold) {
        productService.sellProduct(productUuid, quantitySold);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter-by-category-and-price-range")
    public ResponseEntity<List<ProductDto>> filterProductsByCategoryAndPriceRange(@RequestParam String categoryUuid, @RequestParam double minPrice, @RequestParam double maxPrice) {
        List<ProductDto> filteredProducts = productService.filterProductsByCategoryAndPriceRange(categoryUuid, minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/search-by-category-and-name")
    public ResponseEntity<List<ProductDto>> searchProductsByCategoryAndName(@RequestParam String categoryUuid, @RequestParam String keyword) {
        List<ProductDto> filteredProducts = productService.searchProductsByCategoryAndName(categoryUuid, keyword);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/get-by-category/{categoryUuid}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String categoryUuid) {
        List<ProductDto> filteredProducts = productService.getProductsByCategory(categoryUuid);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String keyword) {
        List<ProductDto> products = productService.searchProductsByName(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> searchProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<ProductDto> products = productService.searchProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<ProductDto>> getProductsByUser(@PathVariable String userUuid) {
        List<ProductDto> products = productService.getProductsByUser(userUuid);
        return ResponseEntity.ok(products);
    }
}
