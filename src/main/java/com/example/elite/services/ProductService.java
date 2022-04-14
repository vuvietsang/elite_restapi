package com.example.elite.services;

import com.example.elite.dto.ProductDTO;
import com.example.elite.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface ProductService {
    public Page<ProductDTO> getAllProducts(Specification<Product> specification, Pageable pageable);
}
