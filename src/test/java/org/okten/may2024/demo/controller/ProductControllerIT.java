package org.okten.may2024.demo.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.service.ProductService;
import org.okten.may2024.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ReviewService reviewService;

    @Captor
    private ArgumentCaptor<ProductDto> productDtoArgumentCaptor;

    @SneakyThrows
    @Test
    void createProduct_withSellerRole() {
        mockMvc
                .perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "test product",
                                    "price": 99.87,
                                    "availability": "AVAILABLE"
                                }
                                """)
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SELLER"))))
                .andExpect(status().is2xxSuccessful());

        verify(productService).createProduct(productDtoArgumentCaptor.capture());

        assertNotNull(productDtoArgumentCaptor.getValue());
    }

    @SneakyThrows
    @Test
    void createProduct_withoutSellerRole() {
        mockMvc
                .perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "test product",
                                    "price": 99.87,
                                    "availability": "AVAILABLE"
                                }
                                """)
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("BUYER"))))
                .andExpect(status().isForbidden());

        verifyNoInteractions(productService);
    }

    @SneakyThrows
    @Test
    void getProduct() {
        when(productService.findById(1L)).thenReturn(Optional.of(new ProductDto()));

        mockMvc.perform(get("/products/1")
                .with(jwt()))
                .andExpect(status().isOk());

        verify(productService).findById(1L);
    }
}