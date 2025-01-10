package org.okten.may2024.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterUserDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        String role
) {
}
