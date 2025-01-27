package org.okten.may2024.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.okten.may2024.api.dto.ReviewDto;
import org.okten.may2024.demo.entity.Review;
import org.okten.may2024.demo.mapper.ReviewMapper;
import org.okten.may2024.demo.mapper.ReviewMapperImpl;
import org.okten.may2024.demo.repository.ReviewRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Spy
    private ReviewMapper reviewMapper = new ReviewMapperImpl();

    @Captor
    private ArgumentCaptor<Review> reviewArgumentCaptor;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void createReview() {
        // given
        Long productId = 5L;
        ReviewDto reviewDto = new ReviewDto()
                .reviewText("super")
                .rating(9);
        when(reviewRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        ReviewDto result = reviewService.createReview(productId, reviewDto);

        // then
        assertNotNull(result);

        verify(reviewRepository).save(reviewArgumentCaptor.capture());
        verifyNoMoreInteractions(reviewRepository);

        Review reviewToSave = reviewArgumentCaptor.getValue();
        assertEquals(productId, reviewToSave.getProductId());
        assertNotNull(reviewToSave.getTimestamp());
    }
}