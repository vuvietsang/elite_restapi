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
public class Orders {
    @Id
    @GeneratedValue
    private int id;
    private boolean status;
    private LocalDate createDate;

    @OneToMany(mappedBy = "orders")
    List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne
    User user = new User();
}
