package org.okten.may2024.demo.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorDto(
        String message,
        OffsetDateTime time
) {
}
