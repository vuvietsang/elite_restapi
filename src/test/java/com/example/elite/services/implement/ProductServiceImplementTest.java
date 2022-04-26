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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Optional<Product> product = Optional.of(Product.builder().id(1L).build());
        Mockito.when(productRepository.findById(1L)).thenReturn(product);
        ProductDto productDTO = modelMapper.map(product.get(), ProductDto.class);
        assertEquals(productService.getProductById(1L),productDTO);
    }

    @Test
    void getAllProducts_WithValidData_ShouldReturnProductDTOPage() {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Specification<Product> spec = builder.build();
        Product product1 = new Product();
        Product product2 = new Product();
        Page<Product> productPage = new PageImpl<>(List.of(product1,product2),pageable,2);
        Mockito.when(productRepository.findAll(spec,pageable)).thenReturn(productPage);
        Page<ProductDto> productDTOPage = productPage.map(product -> modelMapper.map(product, ProductDto.class));
        Assertions.assertEquals(productService.getAllProducts(spec,pageable),productDTOPage);
    }

    @Test
    void addProduct_WithValidData_ShouldReturnProductDTO() {
        //given
        Category category = Category.builder()
                .id(123)
                .name("HIHI")
                .description("thom ngon moi an nha toi day khong cho ban nua gio toi an lien")
                .build();
        Product product = Product.builder()
                .name("Apple")
                .createDate(LocalDate.now())
                .category(category)
                .image("hihi")
                .price(5000)
                .quantity(100)
                .description("yummy")
                .status(true).build();
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);
        Mockito.when(productRepository.findByName("Apple")).thenReturn(null);
        Mockito.when(categoryRepository.findByName("HIHI")).thenReturn(category);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Assertions.assertEquals(productService.addProduct(productDTO), productDTO);
    }

    @Test
    void updateProduct_WithValidData_ShouldReturnProductDTO() {
        Optional<Product> product =Optional.of(new Product());
        Category category = new Category();
        category.setName("Fruits");
        product.get().setId(1L);
        product.get().setStatus(false);
        product.get().setDescription("HIHI");
        product.get().setPrice(123);
        product.get().setImage("Image");
        product.get().setName("Edited");
        product.get().setCategory(category);
        ProductDto productDTO = modelMapper.map(product.get(), ProductDto.class);
        Mockito.when(productRepository.findById(1L)).thenReturn(product);
        Mockito.when(categoryRepository.findByName(productDTO.getCategoryName())).thenReturn(category);
        Mockito.when(productRepository.save(product.get())).thenReturn(product.get());
        Assertions.assertEquals(productService.updateProduct(productDTO,1L),productDTO);
    }
    @Test
    void deleteProduct_WithValidData_ShouldReturnProductDTO() {
        Optional<Product> product = Optional.of(new Product());
        product.get().setId(1L);
        product.get().setStatus(true);

        Mockito.when(productRepository.findById(product.get().getId())).thenReturn(product);
        Mockito.when(productRepository.save(product.get())).thenReturn(product.get());
        ProductDto dto = modelMapper.map(product.get() , ProductDto.class);
        dto.setStatus(false);

        Assertions.assertEquals(productService.deleteProduct(1L) ,dto);
        Assertions.assertFalse(productService.deleteProduct(1L).isStatus() );
    }
}