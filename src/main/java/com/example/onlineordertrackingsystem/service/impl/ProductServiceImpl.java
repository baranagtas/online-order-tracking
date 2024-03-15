package com.example.onlineordertrackingsystem.service.impl;

import com.example.onlineordertrackingsystem.dto.ProductDto;
import com.example.onlineordertrackingsystem.model.Category;
import com.example.onlineordertrackingsystem.model.Product;
import com.example.onlineordertrackingsystem.repository.CategoryRepository;
import com.example.onlineordertrackingsystem.repository.ProductRepository;
import com.example.onlineordertrackingsystem.service.CategoryService;
import com.example.onlineordertrackingsystem.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    @Override
    public String saveProduct(ProductDto productDto) {
        // Dto'dan entity'ye dönüştürme ve kaydetme işlemi
        Optional<Category> category = categoryRepository.findByUuid(productDto.getCategoryUuid());
        if (category.isEmpty()) {
            throw new RuntimeException("Category not found with this UUID: " + productDto.getCategoryUuid());
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setUuid(UUID.randomUUID().toString());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCreatedAt(LocalDate.now());
        product.setCategory(category.get());

        productRepository.save(product);

        return "Product created successfully";
    }

    @Override
    public String updateProduct(String uuid, ProductDto updatedProductDto) {
        // Gerekli güncelleme işlemleri
        Optional<Product> productOptional = productRepository.findByUuid(uuid);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            // updatedProductDto içinden alınan verilerle mevcut ürünü güncelleme
            existingProduct.setName(updatedProductDto.getName());
            existingProduct.setDescription(updatedProductDto.getDescription());
            existingProduct.setPrice(updatedProductDto.getPrice());
            existingProduct.setStockQuantity(updatedProductDto.getStockQuantity());
            existingProduct.setUpdatedAt(LocalDate.now()); // Son güncelleme tarihini ayarlama
            productRepository.save(existingProduct); // Güncellenmiş ürünü kaydetme
            return "Product is updated";
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    /**
     * Deletes a product with the given UUID.
     *
     * @param uuid The UUID of the product to be deleted.
     * @return A message indicating the success of the deletion operation.
     * @throws RuntimeException If the product with the given UUID is not found.
     */
    @Override
    public String deleteProduct(String uuid) {
        // Retrieve the product for deletion by UUID
        Optional<Product> productForDeletion = productRepository.findByUuid(uuid);

        // Check if the product exists
        if (productForDeletion.isEmpty()) {
            // If product does not exist, throw a runtime exception
            throw new RuntimeException("Product not found");
        }

        // Delete the product
        productRepository.delete(productForDeletion.get());

        // Return a success message
        return "Product is deleted";
    }


    @Override
    public List<ProductDto> filterProductsByCategory(Category category) {
        // Kategoriye göre filtreleme işlemi
        List<Product> products = productRepository.findByCategoryUuid(category.getUuid());
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductsByName(String keyword) {
        // İsme göre arama işlemi
        List<Product> products = productRepository.findByNameContaining(keyword);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductsByPriceRange(double minPrice, double maxPrice) {
        // Fiyat aralığına göre filtreleme işlemi
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByPrice(double price) {
        // Fiyata göre filtreleme işlemi
        List<Product> products = productRepository.findByPrice(price);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByUser(String userUuid) {
        // Kullanıcıya göre ürünleri getirme işlemi
        List<Product> products = productRepository.findByUserUuid(userUuid);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStockQuantity(String productUuid, int newQuantity) {
        Optional<Product> productOptional = productRepository.findByUuid(productUuid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setStockQuantity(newQuantity);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with UUID: " + productUuid);
        }
    }//iki method olacak ürün iade edildiğinde ürünün stoğunu güncellesin satıldığında da

    @Override
    public void returnProduct(String productUuid, int quantityReturned) {
        Optional<Product> productOptional = productRepository.findByUuid(productUuid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int currentStock = product.getStockQuantity();
            int newStock = currentStock + quantityReturned;
            product.setStockQuantity(newStock);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with UUID: " + productUuid);
        }
    }

    @Override
    public void sellProduct(String productUuid, int quantitySold) {
        Optional<Product> productOptional = productRepository.findByUuid(productUuid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int currentStock = product.getStockQuantity();
            int newStock = currentStock - quantitySold;
            if (newStock >= 0) {
                product.setStockQuantity(newStock);
                productRepository.save(product);
            } else {
                throw new RuntimeException("Insufficient stock for product with UUID: " + productUuid);
            }
        } else {
            throw new RuntimeException("Product not found with UUID: " + productUuid);
        }
    }

    @Override
    public List<ProductDto> filterProductsByCategoryAndPriceRange(String categoryUuid, double minPrice, double maxPrice) {
        // Belirli bir kategoriye ve fiyat aralığına göre ürünleri filtreleme işlemi
        List<Product> products = productRepository.findByCategoryUuidAndPriceBetween(categoryUuid, minPrice, maxPrice);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductsByCategoryAndName(String categoryUuid, String keyword) {
        // Belirli bir kategoride ve isme göre ürünleri arama işlemi
        List<Product> products = productRepository.findByCategoryUuidAndNameContaining(categoryUuid, keyword);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryUuid) {
        // Belirli bir kategoriye göre ürünleri getirme işlemi
        List<Product> products = productRepository.findByCategoryUuid(categoryUuid);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;    }


    private ProductDto convertToDto(Product product) {
        // Entity'den Dto'ya dönüştürme işlemi
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
}
