package org.okten.may2024.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.CreateReviewDto;
import org.okten.may2024.demo.dto.ReviewDto;
import org.okten.may2024.demo.entity.Product;
import org.okten.may2024.demo.repository.ProductRepository;
import org.okten.may2024.demo.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final ReviewService reviewService;

    @Secured("SELLER")
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

    @PostMapping("/products/{id}/reviews")
    public ReviewDto createReview(@PathVariable(name = "id") Long productId, @RequestBody @Valid CreateReviewDto createReviewDto) {
        return reviewService.createReview(productId, createReviewDto);
    }

    @GetMapping("/products/{productId}/reviews")
    public List<ReviewDto> getReviews(@PathVariable Long productId) {
        return reviewService.getReviews(productId);
    }
}
