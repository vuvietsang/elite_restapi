package com.example.elite.dto;

import com.example.elite.entities.Category;
import com.example.elite.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private long id;
    @Size(min=3)
    private String name;
    @Max(1000)
    private int quantity;
    @Size(min=10)
    private String description;
    @Min(100)
    private double price;
    private boolean status;
    @NotBlank(message = "Image can not be empty")
    private String image;
    private LocalDate createDate;
    private LocalDate updateDate;
    @NotBlank(message = "Category can not be empty")
    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
