package org.okten.may2024.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.may2024.demo.dto.CreateReviewDto;
import org.okten.may2024.demo.dto.ReviewDto;
import org.okten.may2024.demo.entity.Review;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface ReviewMapper {

    @Mapping(target = "text", source = "createReviewDto.reviewText")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    Review mapToReview(Long productId, CreateReviewDto createReviewDto);

    ReviewDto mapToDto(Review review);
}
