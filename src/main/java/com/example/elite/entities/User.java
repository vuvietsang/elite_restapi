package com.example.elite.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean status;
    private LocalDate createDate;

    @OneToMany(mappedBy = "user")
    List<Orders> ordersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    Role role = new Role();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
