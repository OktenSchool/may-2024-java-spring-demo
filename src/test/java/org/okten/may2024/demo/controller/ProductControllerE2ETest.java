package org.okten.may2024.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.config.TestcontainersConfig;
import org.okten.may2024.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TestcontainersConfig.class})
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class ProductControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void createProduct_withSellerRole() {
        String response = mockMvc
                .perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "test product",
                                    "price": 99.87,
                                    "availability": "AVAILABLE"
                                }
                                """)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SELLER"))))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        ProductDto productDto = objectMapper.readValue(response, ProductDto.class);

        assertTrue(productRepository.existsById(productDto.getId()));
        assertNotNull(productDto.getName());
    }
}