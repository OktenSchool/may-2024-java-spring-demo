package org.okten.may2024.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    @JsonIgnore
    private OrderItemId id;

    private Integer quantity;

    private String comment;
}
