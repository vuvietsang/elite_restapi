package com.example.elite.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @GeneratedValue
    @Id
    private long id;
    private String name;
    private int quantity;
    private String description;
    private double price;
    private String image;
    private LocalDate createDate;
    private LocalDate updateDate;
    private boolean status;

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<Rating> ratings = new ArrayList<>();

    @ManyToOne
    Category category = new Category();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
