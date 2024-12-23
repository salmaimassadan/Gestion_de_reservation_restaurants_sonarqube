package com.example.JEE.services;

import com.example.JEE.entities.Review;
import com.example.JEE.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void deleteReview() {
        // Arrange
        int reviewId = 1;
        doNothing().when(reviewRepository).deleteById(reviewId);

        // Act
        reviewService.deleteReview(reviewId);

        // Assert
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }
}
