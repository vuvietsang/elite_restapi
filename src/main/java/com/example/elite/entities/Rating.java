package com.example.elite.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

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
    private LocalDate ratingDate;

    @ManyToOne
    Product product = new Product();
    @ManyToOne
    User user = new User();

}
