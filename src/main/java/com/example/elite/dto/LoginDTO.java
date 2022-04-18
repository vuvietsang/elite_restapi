package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @Size(min =6,message = "Username length must be greater than 6 characters")
    private String username;

    @Size(min =6,message = "Password length must be greater than 6 characters")
    private String password;
}
