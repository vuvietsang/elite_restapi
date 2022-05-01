package com.example.elite.controller.admin;

import com.example.elite.dto.ProductDto;
import com.example.elite.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> updateProduct(@Valid @RequestBody ProductDto dto, @PathVariable(name = "id") Long id) {
        ResponseDto<ProductDto> responseDTO = new ResponseDto();
        ProductDto dtoReturn = productService.updateProduct(dto, id);
        responseDTO.setSuccessMessage("UPDATE PRODUCT SUCCESSFULLY!");
        responseDTO.setData(dtoReturn);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable(name = "id") Long id) {
        ResponseDto<ProductDto> responseDTO = new ResponseDto();
        responseDTO.setSuccessMessage("DELETE PRODUCT SUCCESSFULLY!");
        responseDTO.setData(productService.deleteProduct(id));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> addNewProduct(@Validated @RequestBody ProductDto dto) {
        ResponseDto<ProductDto> responseDTO = new ResponseDto();
        ProductDto dtoTMp = productService.addProduct(dto);
        if (dtoTMp != null) {
            responseDTO.setSuccessMessage("ADD PRODUCT SUCCESSFULLY!");
            responseDTO.setData(dtoTMp);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
