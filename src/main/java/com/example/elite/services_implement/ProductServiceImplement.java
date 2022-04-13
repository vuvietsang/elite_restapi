package com.example.elite.services_implement;

import com.example.elite.dto.ProductDTO;
import com.example.elite.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.awt.print.Pageable;

public class ProductServiceImplement implements ProductService {

    @Override
    public Page<ProductDTO> getAllProducts(Specification<ProductDTO> specification, Pageable pageable) {
        return null;
    }
}
