package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private int userId;
    private String fullName;
    private String username;
    private String email;
    private String roleName;
    private String phone;
    private boolean status;
    private String avatar;
}
