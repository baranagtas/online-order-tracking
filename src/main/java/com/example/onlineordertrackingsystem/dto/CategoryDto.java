package com.example.onlineordertrackingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String uuid;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String productUuid;
//    private List<Object> productList;
}
