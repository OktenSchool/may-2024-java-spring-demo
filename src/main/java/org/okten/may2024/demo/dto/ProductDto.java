package org.okten.may2024.demo.dto;

import lombok.Builder;

@Builder
public record ProductDto(
        Long id,
        String name,
        Double price
) {
}
