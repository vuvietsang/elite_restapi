package com.example.elite.controller.user;

import com.example.elite.dto.ProductDto;
import com.example.elite.dto.RatingDto;
import com.example.elite.dto.ResponseDto;
import com.example.elite.entities.Rating;
import com.example.elite.services.ProductService;
import com.example.elite.services.RatingService;
import com.example.elite.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllProduct(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam String search,
                                                     @RequestParam String sort) {
        ResponseDto<Page<ProductDto>> responseDTO = new ResponseDto();
        Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(9), sort.equals("createDate") ? Sort.by("createDate").descending() : Sort.by(!sort.equals("") ? sort : "id").ascending());
        Specification spec = Utils.buildProductSpecifications(search);
        responseDTO.setData(productService.getAllProducts(spec, pageable));
        responseDTO.setSuccessMessage("GET ALL PRODUCTS SUCCESSFULLY");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductById(@PathVariable(value = "id") Long id) {
        ResponseDto<ProductDto> responseDTO = new ResponseDto();
        responseDTO.setData(productService.getProductById(id));
        responseDTO.setSuccessMessage("GET PRODUCT SUCCESSFULLY!");
        return ResponseEntity.ok().body(responseDTO);
    }
    @GetMapping("/rating/{id}")
    public ResponseEntity<ResponseDto> getAllRatingByProductId(@PathVariable(value = "id") Long id,@RequestParam Integer pageNumber,
                                                               @RequestParam Integer pageSize) {
        ResponseDto<Page<RatingDto>> responseDTO = new ResponseDto();
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        responseDTO.setData(ratingService.getAllRatingByProductId(id,pageable));
        responseDTO.setSuccessMessage("GET RATING SUCCESSFULLY!");
        return ResponseEntity.ok().body(responseDTO);
    }
}
