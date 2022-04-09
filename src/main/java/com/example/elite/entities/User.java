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
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private boolean status;
    private LocalDate createDate;

    @OneToMany(mappedBy = "user")
    List<Orders> ordersList = new ArrayList<>();

    @ManyToOne
    Role role = new Role();
}
