package org.okten.may2024.demo.dto;

import lombok.Builder;

@Builder
public record UserLoginResponseDto(
        String accessToken,
        String refreshToken
) {
}
