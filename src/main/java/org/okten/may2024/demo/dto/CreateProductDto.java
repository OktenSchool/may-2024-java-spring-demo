package org.okten.may2024.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateProductDto(
        @NotBlank
        String name,

        @DecimalMin(value = "0", inclusive = false)
        Double price
) {
}
