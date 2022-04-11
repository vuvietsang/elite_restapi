package com.example.elite.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    List<Product> productList= new ArrayList<>();
}
