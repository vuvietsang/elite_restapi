package com.example.elite.services_implement;

import com.example.elite.dto.ProductDTO;
import com.example.elite.entities.Product;
import com.example.elite.repository.ProductRepository;
import com.example.elite.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Specification<Product> specification, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Page<Product> pageProduct = productRepository.findAll(specification,pageable);
        Page<ProductDTO> pageUserDTO = pageProduct.map(product->modelMapper.map(product,ProductDTO.class));
        return pageUserDTO;
    }
}
