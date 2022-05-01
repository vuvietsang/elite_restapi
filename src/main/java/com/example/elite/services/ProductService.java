package com.example.elite.services;

import com.example.elite.dto.ProductDto;
import com.example.elite.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface ProductService {
    public Page<ProductDto> getAllProducts(Specification<Product> specification, Pageable pageable);

    public ProductDto addProduct(ProductDto dto);

    public ProductDto updateProduct(ProductDto dto, Long productId);

    public ProductDto deleteProduct(Long productId);

    public ProductDto getProductById(Long id);
}
