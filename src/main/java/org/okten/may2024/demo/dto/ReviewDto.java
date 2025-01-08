package org.okten.may2024.demo.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewDto(
        String text,
        Integer rating,
        LocalDateTime timestamp
) {
}
