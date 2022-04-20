package com.example.elite.services.implement;

import com.example.elite.dto.ProductDTO;
import com.example.elite.entities.Category;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.repository.CategoryRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.services.ProductService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTest {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productServiceImplement ;
    @Mock
    private Pageable pageable;


    @Test
    void getProductById() {
        productRepository.getById(1L);
        Mockito.verify(productRepository).getById(1L);
    }

    @Test
    void getAllProducts() {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Specification<Product> spec = builder.build();
        productRepository.findAll(spec,pageable);
        Mockito.verify(productRepository).findAll(spec,pageable);
    }

    @Test
    void addProduct() {
        //given
        Category category = Category.builder().id(123).name("HIHI").description("thom ngon moi an nha toi day khong cho ban nua gio toi an lien").build();
        Product product = Product.builder()
                .name("Apple")
                .createDate(LocalDate.now())
                .category(category)
                .image("hihi")
                .price(5000)
                .quantity(100)
                .description("yummy")
                .status(true).build();
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);


        Mockito.when(productRepository.findByName("Apple")).thenReturn(null);
        Mockito.when(categoryRepository.findByName("HIHI")).thenReturn(category);
        Mockito.when(productRepository.save(product)).thenReturn(product);

       Assertions.assertEquals(productServiceImplement.addProduct(productDTO) , product);


    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }


}