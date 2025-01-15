package org.okten.may2024.demo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.api.controller.ProductsApi;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.api.dto.ReviewDto;
import org.okten.may2024.demo.service.ProductService;
import org.okten.may2024.demo.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductService productService;

    private final ReviewService reviewService;

    @Secured("SELLER")
    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(productService.findById(id)
                .map(productDto -> {
                    List<ReviewDto> reviews = reviewService.getReviews(id);
                    productDto.setReviews(reviews);
                    return productDto;
                }));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProducts(Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceBetween(minPrice, maxPrice));
        } else if (minPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceGreaterThan(minPrice));
        } else if (maxPrice != null) {
            return ResponseEntity.ok(productService.findAllByPriceLessThan(maxPrice));
        } else {
            return ResponseEntity.ok(productService.findAll());
        }
    }

    @Override
    public ResponseEntity<ProductDto> modifyProduct(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.update(id, productDto));
    }

    @Override
    public ResponseEntity<ProductDto> modifyProductPartially(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.updatePartially(id, productDto));
    }

    @Override
    public ResponseEntity<ReviewDto> postReview(Long productId, ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(productId, reviewDto));
    }
}
