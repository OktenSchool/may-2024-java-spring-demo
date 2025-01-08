package org.okten.may2024.demo.repository;

import org.bson.types.ObjectId;
import org.okten.may2024.demo.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findAllByProductId(Long productId);

    List<Review> findAllByTimestampAfter(LocalDateTime from);
}
