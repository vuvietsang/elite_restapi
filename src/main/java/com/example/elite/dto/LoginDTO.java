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

    @NotBlank
    @NotNull(message = "Name cannot be null")
    @Size(min =6)
    private String username;

    @NotBlank
    @NotNull(message = "Password cannot be null")
    @Size(min =6)
    private String password;
}
