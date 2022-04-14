package com.example.elite.dto;

import com.example.elite.entities.Category;
import com.example.elite.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private long id;
    private String name;
    private int quantity;
    private String description;
    private double price;
    private String image;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String categoryName;
}
