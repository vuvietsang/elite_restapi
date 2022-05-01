package com.example.elite.services.implement;

import com.example.elite.dto.ProductDto;
import com.example.elite.entities.Category;
import com.example.elite.entities.Product;
import com.example.elite.exceptions.CategoryNotFoundException;
import com.example.elite.exceptions.ProductNameExistException;
import com.example.elite.repository.CategoryRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ProductDto> getAllProducts(Specification<Product> specification, Pageable pageable) {
        Page<Product> pageProduct = productRepository.findAll(specification, pageable);
        Page<ProductDto> pageProductDTO = pageProduct.map(product -> modelMapper.map(product, ProductDto.class));
        return pageProductDTO;
    }

    @Override
    public ProductDto addProduct(ProductDto dto) {
        Product productExist = productRepository.findByName(dto.getName());
        if(productExist!=null){
            throw new ProductNameExistException("THIS PRODUCT NAME EXISTED!");
        }
        Category category =categoryRepository.findByName(dto.getCategoryName());
        if(category==null){
            throw new CategoryNotFoundException("CATEGORY NOT FOUND!");
        }
        Product product = Product.builder()
                .category(category)
                .createDate(LocalDate.now())
                .description(dto.getDescription())
                .image(dto.getImage())
                .price(dto.getPrice())
                .status(true)
                .quantity(dto.getQuantity())
                .name(dto.getName()).build();
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto dto, Long productId) throws NoSuchElementException {
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
        return modelMapper.map(product.get(), ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Long productId)  throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(productId);
        product.get().setStatus(false);
        return modelMapper.map(productRepository.save(product.get()), ProductDto.class) ;
    }

    @Override
    public ProductDto getProductById(Long id) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(id);
        ProductDto productDTO = modelMapper.map(product.get(), ProductDto.class);
        return productDTO;
    }
}
