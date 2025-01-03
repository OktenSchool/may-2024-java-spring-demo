package org.okten.may2024.demo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.entity.Product;
import org.okten.may2024.demo.repository.ProductRepository;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(productRepository.findById(id));
    }

    @GetMapping(value = "/products", produces = "application/json")
    public List<Product> getProducts(
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {
        if (minPrice != null && maxPrice != null) {
            return productRepository.findAllByPriceBetweenWithSql(minPrice, maxPrice);
        } else if (minPrice != null) {
            return productRepository.findAllByPriceGreaterThan(minPrice);
        } else if (maxPrice != null) {
            return productRepository.findAllByPriceLessThan(maxPrice);
        } else {
            return productRepository.findAll();
        }
    }
}
