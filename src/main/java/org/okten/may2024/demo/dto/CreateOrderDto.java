package org.okten.may2024.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateOrderDto(
        @NotEmpty
        List<@Valid CreateOrderItemDto> items
) {

    @Builder
    public record CreateOrderItemDto(
            @NotNull
            Long productId,

            @Min(1)
            Integer quantity,

            String comment
    ) {
    }
}
