package com.example.elite.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String roleName;

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY )
    List<User> userList = new ArrayList<>();
}
