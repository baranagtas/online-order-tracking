package com.example.onlineordertrackingsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String uuid;
    private String description;
    private double price;
    private int stockQuantity;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String categoryName;
    private String categoryUuid;
}
