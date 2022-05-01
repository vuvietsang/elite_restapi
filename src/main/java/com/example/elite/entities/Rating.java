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
public class Rating {
    @Id
    @GeneratedValue
    private int id;
    private String comment;
    private int ratingStar;

    @ManyToOne
    Product product = new Product();
    @ManyToOne
    User user = new User();

}
