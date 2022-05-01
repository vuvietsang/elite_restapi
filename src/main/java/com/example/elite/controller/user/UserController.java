package com.example.elite.controller.user;

import com.example.elite.dto.LoginDto;
import com.example.elite.dto.LoginResponseDto;
import com.example.elite.dto.ResponseDto;
import com.example.elite.entities.User;
import com.example.elite.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("user")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Validated @RequestBody LoginDto user) {
        ResponseDto<LoginResponseDto> responseDTO = new ResponseDto();
        LoginResponseDto loginResponseDTO = null;
        loginResponseDTO = userService.login(user);
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessMessage("LOGIN_SUCCESS");
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Validated @RequestBody User user) throws RoleNotFoundException {
        ResponseDto<LoginResponseDto> responseDTO = new ResponseDto();
        LoginResponseDto loginResponseDTO = userService.register(user);
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessMessage("REGISTER_SUCCESS");
        return ResponseEntity.ok().body(responseDTO);
    }
}
