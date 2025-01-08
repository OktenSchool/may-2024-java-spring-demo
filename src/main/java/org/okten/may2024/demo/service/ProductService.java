package org.okten.may2024.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.ProductDto;
import org.okten.may2024.demo.mapper.ProductMapper;
import org.okten.may2024.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Optional<ProductDto> findById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::mapToDto);
    }
}
