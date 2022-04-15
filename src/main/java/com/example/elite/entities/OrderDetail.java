package com.example.elite.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue
    private int id;
    private int quantity;
    private double price;

    @ManyToOne
    Product product = new Product();

    @ManyToOne
    Orders orders = new Orders();
}
