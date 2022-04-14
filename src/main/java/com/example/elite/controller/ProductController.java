package com.example.elite.controller;

import com.example.elite.dto.ProductDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllProduct(@RequestParam Integer pageNum,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam String search){
        ResponseDTO  responseDTO = new ResponseDTO();
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNum).orElse(0),Optional.ofNullable(pageSize).orElse(10));
            ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            Specification<Product> spec = builder.build();
            Page<ProductDTO> page = productService.getAllProducts(spec,  pageable);
            if(page.isEmpty()){
                    responseDTO.setSuccessCode("NO PRODUCTS!");
            }
            else {
                responseDTO.setData(page);
                responseDTO.setSuccessCode("GET ALL PRODUCTS SUCCESSFULLY");
                return ResponseEntity.ok().body(responseDTO);
            }
        }
        catch (Exception e){
            responseDTO.setErrorCode(e.getMessage());
        }
        return ResponseEntity.status(400).body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody ProductDTO dto, @PathVariable(name="id")  Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = productService.updateProduct(dto,id);
        int status = 400;
        if(check){
            responseDTO.setSuccessCode("UPDATE PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = 200;
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
        int status = 400;
        if(check){
            responseDTO.setSuccessCode("DELETE PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = 200;
        }
        else {
            responseDTO.setErrorCode("DELETE PRODUCT FAILED!");
            responseDTO.setData(false);
        }
        return ResponseEntity.status(status).body(responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDTO> addNewProduct(@RequestBody ProductDTO dto){
        ResponseDTO responseDTO = new ResponseDTO();
        boolean check = productService.addProduct(dto);
        int status = 400;
        if(check){
            responseDTO.setSuccessCode("ADD PRODUCT SUCCESSFULLY!");
            responseDTO.setData(true);
            status = 200;
        }
        else {
            responseDTO.setErrorCode("ADD PRODUCT FAILED!");
            responseDTO.setData(false);
        }
        return ResponseEntity.status(status).body(responseDTO);
    }
}
