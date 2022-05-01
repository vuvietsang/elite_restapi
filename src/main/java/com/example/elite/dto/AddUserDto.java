package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddUserDto {
    @NotBlank(message = "Fullname can not be null")
    private String fullName;
    @NotBlank(message = "Username can not be null")
    private String username;
    @NotBlank(message = "Email can not be null")
    private String email;
    @NotBlank(message = "Role name can not be null")
    private String roleName;
    @Size(min = 9)
    private String phone;
    @Size(min = 6, message = "Password should have at least 6 characters!")
    private String password;
    private String avatar;
}
