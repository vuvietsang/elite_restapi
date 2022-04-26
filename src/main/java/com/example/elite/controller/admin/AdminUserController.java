package com.example.elite.controller.admin;

import com.example.elite.dto.AddUserDto;
import com.example.elite.dto.ProductDto;
import com.example.elite.dto.ResponseDto;
import com.example.elite.dto.UserDto;
import com.example.elite.entities.User;
import com.example.elite.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("user")
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class AdminUserController {
    private UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable(name="id") int userId){
        ResponseDto<UserDto> responseDTO = new ResponseDto();
        UserDto checkDTO = userService.deleteUserById(userId);
        if (checkDTO != null) {
            responseDTO.setSuccessMessage("DELETE SUCCESSFULLY");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@Validated @RequestBody AddUserDto user) {
        ResponseDto<UserDto> responseDTO = new ResponseDto();
        UserDto checkDTO = userService.addUser(user);
        if (checkDTO != null) {
            responseDTO.setSuccessMessage("ADD SUCCESSFULLY");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDto> update(@Validated @RequestBody User user, @PathVariable(name="id") int id) {
        ResponseDto<UserDto> responseDTO = new ResponseDto();
        UserDto checkDTO = userService.updateUser(user, id);
        if (checkDTO != null) {
            responseDTO.setSuccessMessage("UPDATE SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        } else {
            responseDTO.setErrorMessage("UPDATE FAIL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<ResponseDto> findAll(@RequestParam int pageNumber, @RequestParam int pageSize){
        ResponseDto<Page<UserDto>> responseDTO = new ResponseDto();
        Page<UserDto> users = userService.getAllUser(pageNumber,pageSize);
            responseDTO.setData(users);
            responseDTO.setSuccessMessage("GET ALL SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseDto> findByUsername(@PathVariable(name="username") String username){
        ResponseDto<UserDto> responseDTO = new ResponseDto();
        UserDto user = userService.findByUserName(username);
        responseDTO.setData(user);
        responseDTO.setSuccessMessage("GET USER SUCCESSFULLY");
        return ResponseEntity.ok().body(responseDTO);
    }
}
