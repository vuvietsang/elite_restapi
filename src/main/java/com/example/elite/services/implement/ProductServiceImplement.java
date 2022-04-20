package com.example.elite.services.implement;

import com.example.elite.dto.ProductDTO;
import com.example.elite.entities.Category;
import com.example.elite.entities.Product;
import com.example.elite.handle_exception.CategoryNotFoundException;
import com.example.elite.handle_exception.ProductNameExistException;
import com.example.elite.repository.CategoryRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.services.ProductService;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
     private ProductRepository productRepository;
    @Autowired
     private CategoryRepository categoryRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Specification<Product> specification, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Page<Product> pageProduct = productRepository.findAll(specification,pageable);
        Page<ProductDTO> pageUserDTO = pageProduct.map(product->modelMapper.map(product,ProductDTO.class));
        return pageUserDTO;
    }

    @Override
    public ProductDTO addProduct(ProductDTO dto) {
        Product product = productRepository.findByName(dto.getName());
        Category category =categoryRepository.findByName(dto.getCategoryName());
        if(category==null){
            throw new CategoryNotFoundException("CATEGORY NOT FOUND!");
        }
        if(product!=null){
            throw new ProductNameExistException("THIS PRODUCT NAME EXISTED!");
        }
        Product product1 = Product.builder()
                .category(category)
                .createDate(LocalDate.now())
                .description(dto.getDescription())
                .image(dto.getImage())
                .price(dto.getPrice())
                .status(true)
                .quantity(dto.getQuantity())
                .name(dto.getName()).build();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productRepository.save(product1),ProductDTO.class);
    }

    @Override
    public boolean updateProduct(ProductDTO dto, Long productId) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(productId);
        Category category =categoryRepository.findByName(dto.getCategoryName());
        if(category==null){
            throw new CategoryNotFoundException("THIS CATEGORY NAME NOT FOUND!");
        }
        product.get().setCategory(category);
        product.get().setUpdateDate(LocalDate.now());
        product.get().setDescription(dto.getDescription());
        product.get().setImage(dto.getImage());
        product.get().setName(dto.getName());
        product.get().setPrice(dto.getPrice());
        product.get().setQuantity(dto.getQuantity());
        productRepository.save(product.get());
        return true;
    }

    @Override
    public boolean deleteProduct(Long productId)  throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(productId);
        product.get().setStatus(false);
        return true;
    }

    @Override
    public ProductDTO getProductById(Long id) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(id);
        ModelMapper  modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product.get(),ProductDTO.class);
        return productDTO;
    }
}
