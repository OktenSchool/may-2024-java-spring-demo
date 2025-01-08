package org.okten.may2024.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateReviewDto(
        @NotBlank
        String reviewText,

        @Min(0)
        @Max(10)
        Integer rating
) {
}
