package com.example.elite.services.implement;

import com.example.elite.dto.ProductDto;
import com.example.elite.entities.Category;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.repository.CategoryRepository;
import com.example.elite.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;


    @InjectMocks
    private ProductServiceImplement productService ;

    private Pageable pageable;
    @Mock
    private ModelMapper modelMapper;



    @Test
    void getProductById_WithValidData_ShouldReturnProductDTO() {
        Optional<Product> product = Optional.of(Mockito.mock(Product.class));
        Optional<ProductDto> productDto = Optional.of(Mockito.mock(ProductDto.class));
        when(productRepository.findById(product.get().getId())).thenReturn(product);
        when(modelMapper.map(product.get(),ProductDto.class)).thenReturn(productDto.get());
        assertEquals(productService.getProductById(product.get().getId()),productDto.get());
    }

    @Test
    void getAllProducts_WithValidData_ShouldReturnProductDTOPage() {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Specification<Product> spec = builder.build();
        Product product1 = new Product();
        Product product2 = new Product();
        Page<Product> productPage = new PageImpl<>(List.of(product1,product2),pageable,2);
        when(productRepository.findAll(spec,pageable)).thenReturn(productPage);
        Page<ProductDto> productDTOPage = productPage.map(product -> modelMapper.map(product, ProductDto.class));
        Assertions.assertEquals(productService.getAllProducts(spec,pageable),productDTOPage);
    }

    @Test
    void addProduct_WithValidData_ShouldReturnProductDTO() {
        Optional<Category> category =Optional.of(Mockito.mock(Category.class));
        Optional<Product> product = Optional.of(Mockito.mock(Product.class));
        Optional<ProductDto> productDto = Optional.of(Mockito.mock(ProductDto.class));

        when(productRepository.findByName(product.get().getName())).thenReturn(null);
        when(categoryRepository.findByName(category.get().getName())).thenReturn(category.get());

        Optional<Product> productSave = Optional.of(Product.builder().name(product.get().getName()).build());

        when(productRepository.save(productSave.get())).thenReturn(productSave.get());

        when(modelMapper.map(productSave.get(),ProductDto.class)).thenReturn(productDto.get());

        Assertions.assertEquals(productService.addProduct(productDto.get()), productDto.get());
    }

    @Test
    void updateProduct_WithValidData_ShouldReturnProductDTO() {
        Optional<Product> product =Optional.of(Mockito.mock(Product.class));
        Optional<Category> category =Optional.of(Mockito.mock(Category.class));
        Optional<ProductDto> productDto =Optional.of(Mockito.mock(ProductDto.class));

        when(productRepository.findById(product.get().getId())).thenReturn(product);
        when(categoryRepository.findByName(productDto.get().getCategoryName())).thenReturn(category.get());

        when(productRepository.save(product.get())).thenReturn(product.get());
        when(modelMapper.map(product.get(),ProductDto.class)).thenReturn(productDto.get());
        Assertions.assertEquals(productService.updateProduct(productDto.get(),product.get().getId()),productDto.get());
    }
    @Test
    void deleteProduct_WithValidData_ShouldReturnProductDTO() {
        Optional<Product> product = Optional.of(Mockito.mock(Product.class));
        Optional<ProductDto> productDto = Optional.of(Mockito.mock(ProductDto.class));
        when(productRepository.findById(product.get().getId())).thenReturn(product);
        when(productRepository.save(product.get())).thenReturn(product.get());
        when(modelMapper.map(product.get(),ProductDto.class)).thenReturn(productDto.get());

        Assertions.assertEquals(productService.deleteProduct(product.get().getId()) ,productDto.get());
        Assertions.assertEquals(productService.deleteProduct(product.get().getId()).isStatus() ,productDto.get().isStatus());
    }
}