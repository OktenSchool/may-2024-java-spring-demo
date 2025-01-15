package org.okten.may2024.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.may2024.api.dto.ReviewDto;
import org.okten.may2024.demo.entity.Review;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface ReviewMapper {

    @Mapping(target = "text", source = "createReviewDto.reviewText")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    Review mapToReview(Long productId, ReviewDto createReviewDto);

    @Mapping(target = "reviewText", source = "text")
    ReviewDto mapToDto(Review review);

    default OffsetDateTime map(LocalDateTime value) {
        return value.atOffset(ZoneOffset.UTC);
    }
}
