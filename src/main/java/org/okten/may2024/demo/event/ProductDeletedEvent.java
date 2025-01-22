package org.okten.may2024.demo.event;

import lombok.Builder;

@Builder
public record ProductDeletedEvent(Long productId) {
}
