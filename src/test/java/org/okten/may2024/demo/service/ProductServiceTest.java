package org.okten.may2024.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.api.event.producer.IProductEventsProducer;
import org.okten.may2024.demo.entity.Product;
import org.okten.may2024.demo.entity.ProductAvailability;
import org.okten.may2024.demo.mapper.ProductMapper;
import org.okten.may2024.demo.mapper.ProductMapperImpl;
import org.okten.may2024.demo.repository.ProductRepository;

import java.util.Optional;

class ProductServiceTest {

    // Mock & Spy

    private ProductRepository productRepository = Mockito.mock();

    private ProductMapper productMapper = new ProductMapperImpl();

    private IProductEventsProducer productEventsProducer = Mockito.mock();

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productMapper, productEventsProducer);
    }

    @Test
    void findById_ifExistsInRepository() {
        Product product = new Product();
        product.setId(1L);
        product.setName("test product");
        product.setPrice(9.99);
        product.setProductAvailability(ProductAvailability.OUT_OF_STOCK);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Optional<ProductDto> result = productService.findById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(product.getName(), result.get().getName());
        Assertions.assertEquals(product.getId(), result.get().getId());
        Assertions.assertEquals(product.getPrice(), result.get().getPrice());
        Assertions.assertEquals(product.getProductAvailability().name(), result.get().getAvailability().name());
    }

    @Test
    void findById_ifNotExistsInRepository() {
        Mockito.when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Optional<ProductDto> result = productService.findById(9L);

        Assertions.assertTrue(result.isEmpty());
    }
}