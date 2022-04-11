package com.example.elite.controller;

import com.example.elite.dto.LoginDTO;
import com.example.elite.dto.LoginResponseDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.entities.User;
import com.example.elite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO=null;
        try {
             loginResponseDTO = userService.login(user);
        }catch (Exception ex){
            responseDTO.setErrorCode(ex.getMessage());
            return ResponseEntity.status(400).body(responseDTO);
        }
        if(loginResponseDTO==null){
            return ResponseEntity.status(400).body(responseDTO);
        }
        else{
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessCode("LOGIN_SUCCESS");
        return ResponseEntity.ok().body(responseDTO);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody User user){
        ResponseDTO responseDTO = new ResponseDTO();
        LoginResponseDTO loginResponseDTO = userService.register(user);
        if(loginResponseDTO==null){
            responseDTO.setErrorCode("REGISTER_FAIL");
            return ResponseEntity.status(400).body(responseDTO);
        }
        else{
            responseDTO.setData(loginResponseDTO);
            responseDTO.setSuccessCode("REGISTER_SUCCESS");
            return ResponseEntity.ok().body(responseDTO);
        }
    }
}
