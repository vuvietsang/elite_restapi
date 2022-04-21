package com.example.elite.services.implement;

import com.example.elite.dto.ProductDTO;
import com.example.elite.entities.Category;
import com.example.elite.entities.Product;
import com.example.elite.filter.ProductSpecificationBuilder;
import com.example.elite.repository.CategoryRepository;
import com.example.elite.repository.ProductRepository;
import com.example.elite.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTest {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService ;
    @Mock
    private Pageable pageable;

    private ModelMapper modelMapper;
    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
        pageable = PageRequest.of(0,10);
    }

    @Test
    void getProductById() {
        Optional<Product> product = Optional.of(new Product());
        Mockito.when(productRepository.findById(1L)).thenReturn(product);
        assertEquals(productService.getProductById(1L) , modelMapper.map(product,ProductDTO.class));
    }

    @Test
    void getAllProducts() {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Specification<Product> spec = builder.build();
        Product product1 = new Product();
        Product product2 = new Product();
        Page<Product> productPage = new PageImpl<>(List.of(product1,product2),pageable,2);
        Page<ProductDTO> pageProductDTO = productPage.map(product->modelMapper.map(product,ProductDTO.class));
        Mockito.when(productRepository.findAll(spec,pageable)).thenReturn(productPage);
        Assertions.assertEquals(productService.getAllProducts(spec,pageable),pageProductDTO);
    }

    @Test
    void addProduct() {
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

        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);
        Mockito.when(productRepository.findByName("Apple")).thenReturn(null);
        Mockito.when(categoryRepository.findByName("HIHI")).thenReturn(category);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Assertions.assertEquals(productService.addProduct(productDTO), productDTO);
    }

    @Test
    void updateProduct() {
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
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);
        Mockito.when(productRepository.findById(1L)).thenReturn(product);
        Mockito.when(categoryRepository.findByName(productDTO.getCategoryName())).thenReturn(category);
        Mockito.when(productRepository.save(product.get())).thenReturn(product.get());
        Assertions.assertEquals(productService.updateProduct(productDTO,1L),productDTO);
    }
    @Test
    void deleteProduct() {
        Optional<Product> product = Optional.of(new Product());
        product.get().setId(1L);
        product.get().setStatus(true);

        Mockito.when(productRepository.findById(product.get().getId())).thenReturn(product);
        Mockito.when(productRepository.save(product.get())).thenReturn(product.get());
        ProductDTO dto = modelMapper.map(product.get() , ProductDTO.class);
        dto.setStatus(false);

        Assertions.assertEquals(productService.deleteProduct(1L) ,dto);
        Assertions.assertFalse(productService.deleteProduct(1L).isStatus() );
    }
}