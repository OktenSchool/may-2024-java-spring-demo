package org.okten.may2024.demo.dto;

import lombok.Builder;

@Builder
public record SendMailDto(
        String to,
        String subject,
        String text
) {
}
