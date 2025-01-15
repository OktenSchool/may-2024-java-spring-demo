package org.okten.may2024.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.may2024.api.dto.ReviewDto;
import org.okten.may2024.demo.dto.SendMailDto;
import org.okten.may2024.demo.service.MailService;
import org.okten.may2024.demo.service.ProductService;
import org.okten.may2024.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//@Component
@RequiredArgsConstructor
@Slf4j
public class SendLatestReviewsSummaryJob {

    private final ReviewService reviewService;

    private final ProductService productService;

    private final MailService mailService;

    @Value("${spring.mail.username}")
    private String to;

    @Scheduled(fixedDelay = 30, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void sendLatestReviewsSummary() {
        log.info("Preparing latest reviews summary...");

        Map<Long, List<ReviewDto>> latestReviews = reviewService.getLatestReviews(LocalDateTime.now().minusSeconds(30));

        if (latestReviews.isEmpty()) {
            log.info("No reviews found for last 30 seconds");
            return;
        }

        String summaries = latestReviews.entrySet()
                .stream()
                .flatMap(entry ->
                        productService
                                .findById(entry.getKey())
                                .stream()
                                .flatMap(productDto -> entry.getValue().stream()
                                        .mapToInt(ReviewDto::getRating)
                                        .average()
                                        .stream()
                                        .mapToObj(averageRating -> "Product '%s' has '%s' average rating for the last 30 seconds".formatted(productDto.getName(), averageRating))
                                ))
                .collect(Collectors.joining("\n"));

        SendMailDto sendMailDto = SendMailDto.builder()
                .to(to)
                .subject("Reviews summary")
                .text(summaries)
                .build();

        mailService.sendMail(sendMailDto);

        log.info("Mail with reviews summary was sent");
    }

    // fixedDelay = 1 hour
    // 1 run - 13:00 - 13:05
    // 2 run - 14:05 - 14:10
    // 3 run - 15:10 - 15:15

    // fixedRate = 1 hour
    // 1 run - 13:00 - 13:05
    // 2 run - 14:00 - 14:05
    // 3 run - 15:00 - 15:05
}
