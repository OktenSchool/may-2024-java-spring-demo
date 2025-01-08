package org.okten.may2024.demo.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document("product-reviews")
public class Review {

    @MongoId
    private ObjectId id;

    private Long productId;

    private Integer rating;

    private String text;

    private LocalDateTime timestamp;
}
