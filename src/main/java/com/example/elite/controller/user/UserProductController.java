package com.example.elite.controller.user;

import com.example.elite.dto.ProductDto;
import com.example.elite.dto.ResponseDto;
import com.example.elite.services.ProductService;
import com.example.elite.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("products")
@AllArgsConstructor
public class UserProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllProduct(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam String search){
            ResponseDto responseDTO = new ResponseDto();
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0),Optional.ofNullable(pageSize).orElse(10));
            Specification spec = Utils.buildProductSpecifications(search);
            Page<ProductDto> page = productService.getAllProducts(spec,  pageable);
            responseDTO.setData(page);
            responseDTO.setSuccessMessage("GET ALL PRODUCTS SUCCESSFULLY");
            return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductById(@PathVariable(value = "id") Long id){
        ResponseDto responseDTO = new ResponseDto();
        responseDTO.setData(productService.getProductById(id));
        responseDTO.setSuccessMessage("GET PRODUCT SUCCESSFULLY!");
        return ResponseEntity.ok().body(responseDTO);
    }
}
