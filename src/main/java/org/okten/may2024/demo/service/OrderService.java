package org.okten.may2024.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.CreateOrderDto;
import org.okten.may2024.demo.dto.OrderDto;
import org.okten.may2024.demo.entity.Order;
import org.okten.may2024.demo.entity.OrderItem;
import org.okten.may2024.demo.entity.OrderItemId;
import org.okten.may2024.demo.mapper.OrderMapper;
import org.okten.may2024.demo.repository.OrderRepository;
import org.okten.may2024.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::mapToDto)
                .toList();
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        Order order = orderRepository.save(new Order());

        List<OrderItem> orderItems = createOrderDto.items()
                .stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(orderItemDto.quantity());
                    orderItem.setComment(orderItemDto.comment());
                    OrderItemId orderItemId = new OrderItemId();
                    orderItemId.setOrder(order);
                    orderItemId.setProduct(productRepository.findById(orderItemDto.productId()).orElseThrow(() -> new IllegalArgumentException("Product not found")));
                    orderItem.setId(orderItemId);
                    return orderItem;
                })
                .collect(toCollection(LinkedList::new));

        order.setItems(orderItems);

        return orderMapper.mapToDto(order);
    }
}
