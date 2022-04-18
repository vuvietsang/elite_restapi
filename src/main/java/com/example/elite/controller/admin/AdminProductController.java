package com.example.elite.controller.admin;

import com.example.elite.dto.ProductDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("products")
@AllArgsConstructor
public class AdminProductController {
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@Valid @RequestBody ProductDTO dto, @PathVariable(name="id")  Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = productService.updateProduct(dto,id);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(check){
            responseDTO.setSuccessCode("UPDATE PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = HttpStatus.OK;
        }
        else {
            responseDTO.setErrorCode("UPDATE PRODUCT FAILED!");
            responseDTO.setData(false);
        }
        return ResponseEntity.status(status).body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable(name="id")  Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = productService.deleteProduct(id);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(check){
            responseDTO.setSuccessCode("DELETE PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = HttpStatus.OK;
        }
        else {
            responseDTO.setErrorCode("DELETE PRODUCT FAILED!");
            responseDTO.setData(false);
        }
        return ResponseEntity.status(status).body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDTO> addNewProduct(@Validated @RequestBody ProductDTO dto){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = productService.addProduct(dto);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(check){
            responseDTO.setSuccessCode("ADD PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = HttpStatus.OK;
        }
        else {
            responseDTO.setErrorCode("ADD PRODUCT FAILED!");
            responseDTO.setData(false);
        }
        return ResponseEntity.status(status).body(responseDTO);
    }
}
