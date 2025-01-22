package org.okten.may2024.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.entity.Product;
import org.okten.may2024.demo.event.ProductDeletedEvent;
import org.okten.may2024.demo.event.ProductEventProducer;
import org.okten.may2024.demo.mapper.ProductMapper;
import org.okten.may2024.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductEventProducer productEventProducer;

    public Optional<ProductDto> findById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::mapToDto);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.mapToEntity(productDto);
        product = productRepository.save(product);
        return productMapper.mapToDto(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

        productEventProducer.produceProductDeletedEvent(new ProductDeletedEvent(id));
    }

    public List<ProductDto> findAllByPriceBetween(Double minPrice, Double maxPrice) {
        return productRepository.findAllByPriceBetween(minPrice, maxPrice).stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAllByPriceGreaterThan(Double minPrice) {
        return productRepository.findAllByPriceGreaterThan(minPrice).stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAllByPriceLessThan(Double minPrice) {
        return productRepository.findAllByPriceLessThan(minPrice).stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    @Transactional
    public Optional<ProductDto> update(Long id, ProductDto updateWith) {
        return productRepository.findById(id)
                .map(product -> productMapper.updateEntity(product, updateWith))
                .map(productMapper::mapToDto);
    }

    @Transactional
    public Optional<ProductDto> updatePartially(Long id, ProductDto updateWith) {
        return productRepository.findById(id)
                .map(product -> productMapper.updateEntityPartially(product, updateWith))
                .map(productMapper::mapToDto);
    }
}
