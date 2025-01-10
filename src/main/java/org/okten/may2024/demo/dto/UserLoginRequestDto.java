package org.okten.may2024.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserLoginRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
