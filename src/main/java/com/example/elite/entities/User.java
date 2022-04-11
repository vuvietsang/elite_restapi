package com.example.elite.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
    @Size(min=6,max=30)
    private String fullName;
    @Size(min=6)
    private String username;
    @Size(min=6)
    private String password;
    @Email
    private String email;
    private boolean status;
    private LocalDate createDate;

    @OneToMany(mappedBy = "user")
    List<Orders> ordersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    Role role = new Role();
}
