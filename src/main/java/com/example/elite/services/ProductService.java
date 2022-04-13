package com.example.elite.services;

import com.example.elite.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.awt.print.Pageable;

public interface ProductService {
    public Page<ProductDTO> getAllProducts(Specification<ProductDTO> specification, Pageable pageable);
}
