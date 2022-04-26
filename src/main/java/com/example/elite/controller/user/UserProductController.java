package com.example.elite.controller.user;

import com.example.elite.dto.ProductDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.services.ProductService;
import com.example.elite.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("products")
@AllArgsConstructor
public class UserProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllProduct(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam String search){
            ResponseDTO  responseDTO = new ResponseDTO();
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0),Optional.ofNullable(pageSize).orElse(10));
            Specification spec = Utils.buildProductSpecifications(search);
            Page<ProductDTO> page = productService.getAllProducts(spec,  pageable);
            responseDTO.setData(page);
            responseDTO.setSuccessMessage("GET ALL PRODUCTS SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable(value = "id") Long id){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(productService.getProductById(id));
        responseDTO.setSuccessMessage("GET PRODUCT SUCCESSFULLY!");
        return ResponseEntity.ok().body(responseDTO);
    }
}
