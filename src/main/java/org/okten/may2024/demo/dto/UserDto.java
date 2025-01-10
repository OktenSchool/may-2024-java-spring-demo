package org.okten.may2024.demo.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Long id,
        String username
) {
}
