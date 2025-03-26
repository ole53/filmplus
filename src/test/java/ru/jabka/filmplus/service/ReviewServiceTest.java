package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.ReviewRepository;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void addReview() {
        Review review = getReview();
        Mockito.when(reviewRepository.insert(review)).thenReturn(review);

        Review result = reviewService.create(review);

        assertThat(result).isEqualTo(review);
        verify(reviewRepository.insert(review));
    }

    @Test
    void addReview_withInvalidData_nullUserId_throwsBadRequestException() {
        Review review = getReview();
        review.setUserId(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> reviewService.create(review)
        );

        assertEquals("Необходимо указать id пользователя!", exception.getMessage());
        verify(reviewRepository, never()).insert(any());
    }

    private Review getReview() {
        return Review.builder()
                .id(1L)
                .filmId(2L)
                .userId(3L)
                .comment("hehe")
                .date_create(LocalDate.of(2020, 3, 24))
                .build();
    }
}