package com.example.onlineordertrackingsystem.repository;

import com.example.onlineordertrackingsystem.model.Category;
import com.example.onlineordertrackingsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUuid(String uuid);

    List<Product> findByCategory(Category category);

    List<Product> findByNameContaining(String keyword);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByPrice(double price);

    List<Product> findByUuidIn(List<String> uuids);

    @Query("SELECT p FROM Product p JOIN p.userLists user WHERE user.uuid = :userUuid")
    List<Product> findByUserUuid(String userUuid);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.uuid = :categoryUuid")
    List<Product> findByCategoryUuid(String categoryUuid);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.uuid = :categoryUuid AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByCategoryUuidAndPriceBetween(String categoryUuid, double minPrice, double maxPrice);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.uuid = :categoryUuid AND p.name LIKE %:keyword%")
    List<Product> findByCategoryUuidAndNameContaining(String categoryUuid, String keyword);
}
