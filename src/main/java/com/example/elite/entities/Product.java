package com.example.elite.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    List<Rating> ratings = new ArrayList<>();

    @ManyToOne
    Category category = new Category();
}
