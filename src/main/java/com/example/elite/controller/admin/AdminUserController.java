package com.example.elite.controller.admin;

import com.example.elite.dto.ResponseDTO;
import com.example.elite.dto.UserDTO;
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
    public ResponseEntity<ResponseDTO> delete(@PathVariable(name="id") String userId){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.deleteUserById(Integer.parseInt(userId));
        if(check){
            responseDTO.setSuccessMessage("DELETE SUCCESSFULLY");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> add(@Validated @RequestBody User user){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.addUser(user);
        if(check) {
            responseDTO.setSuccessMessage("ADD SUCCESSFULLY");
        }
        return ResponseEntity.ok().body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@Validated @RequestBody User user, @PathVariable(name="id") int id){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = userService.updateUser(user,id);
        if(check){
            responseDTO.setSuccessMessage("UPDATE SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorMessage("UPDATE FAIL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pageNum={num}&pageSize={size}")
    public ResponseEntity<ResponseDTO> finAll(@PathVariable(name="num") int pageNum, @PathVariable(name="size") int pageSize){
        ResponseDTO responseDTO = new ResponseDTO();

        Page<UserDTO> users = userService.getAllUser(pageNum,pageSize);
        if(users!=null){
            responseDTO.setData(users);
            responseDTO.setSuccessMessage("GET ALL SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
        }
        else  {
            responseDTO.setErrorMessage("GET ALL FAIL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }
}
