package com.example.elite.dto;

import com.example.elite.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private int userId;
    private String fullName;
    private String username;
    private String email;
    private String roleName;
    private String token;
    private String phone;
    private String avatar;
}
