package org.okten.may2024.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.CreateOrderDto;
import org.okten.may2024.demo.dto.OrderDto;
import org.okten.may2024.demo.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    public OrderDto createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        return orderService.createOrder(createOrderDto);
    }
}
