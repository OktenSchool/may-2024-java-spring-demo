package org.okten.may2024.demo.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<Integer, ProductDeletedEvent> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private final String topic;

    public void produceProductDeletedEvent(ProductDeletedEvent event) {
        log.info("Producing event to '{}' with payload {}", topic, event);
        kafkaTemplate.send(topic, event);
    }
}
