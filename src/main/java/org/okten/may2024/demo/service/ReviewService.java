package org.okten.may2024.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.CreateReviewDto;
import org.okten.may2024.demo.dto.ReviewDto;
import org.okten.may2024.demo.entity.Review;
import org.okten.may2024.demo.mapper.ReviewMapper;
import org.okten.may2024.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public ReviewDto createReview(Long productId, CreateReviewDto dto) {
        Review review = reviewMapper.mapToReview(productId, dto);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.mapToDto(savedReview);
    }

    public List<ReviewDto> getReviews(Long productId) {
        return reviewRepository.findAllByProductId(productId)
                .stream()
                .map(reviewMapper::mapToDto)
                .toList();
    }

    public Map<Long, List<ReviewDto>> getLatestReviews(LocalDateTime from) {
        return reviewRepository.findAllByTimestampAfter(from)
                .stream()
                .collect(groupingBy(Review::getProductId, mapping(reviewMapper::mapToDto, toList())));
    }
}
