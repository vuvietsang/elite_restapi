package com.example.elite.controller.user;

import com.example.elite.dto.ProductDTO;
import com.example.elite.dto.ResponseDTO;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.services.ProductService;
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
                responseDTO.setSuccessMessage("NO PRODUCTS!");
            }
            else {
                responseDTO.setData(page);
                responseDTO.setSuccessMessage("GET ALL PRODUCTS SUCCESSFULLY");
                return ResponseEntity.ok().body(responseDTO);
            }
        }
        catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
