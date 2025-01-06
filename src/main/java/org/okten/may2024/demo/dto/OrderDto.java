package org.okten.may2024.demo.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDto(
        Long id,
        List<OrderItemDto> items,
        Double total
) {

    @Builder
    public record OrderItemDto(
            String productName,
            Integer quantity,
            String comment,
            Double subTotal
    ) {
    }
}
