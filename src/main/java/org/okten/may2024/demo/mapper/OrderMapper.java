package org.okten.may2024.demo.mapper;

import org.okten.may2024.demo.dto.OrderDto;
import org.okten.may2024.demo.entity.Order;
import org.okten.may2024.demo.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderDto mapToDto(Order order) {
        List<OrderDto.OrderItemDto> orderItemDtos = order.getItems()
                .stream()
                .map(orderItem -> {
                    Product orderItemProduct = orderItem.getId().getProduct();
                    return OrderDto.OrderItemDto.builder()
                            .productName(orderItemProduct.getName())
                            .quantity(orderItem.getQuantity())
                            .comment(orderItem.getComment())
                            .subTotal(orderItemProduct.getPrice() * orderItem.getQuantity())
                            .build();
                })
                .toList();
        return OrderDto.builder()
                .id(order.getId())
                .items(orderItemDtos)
                .total(orderItemDtos.stream().mapToDouble(OrderDto.OrderItemDto::subTotal).sum())
                .build();
    }
}
