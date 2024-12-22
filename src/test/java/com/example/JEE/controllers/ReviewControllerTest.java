package com.example.JEE.controllers;

import com.example.JEE.entities.Review;
import com.example.JEE.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void createReview_ValidReview_ReturnsCreatedReview() throws Exception {
        Review review = new Review(1, 1, 1, 8, "Great restaurant!");
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"restaurantID\":1,\"userID\":1,\"rating\":8,\"comments\":\"Great restaurant!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(8))
                .andExpect(jsonPath("$.comments").value("Great restaurant!"));

        verify(reviewService, times(1)).createReview(any(Review.class));
    }

    @Test
    void createReview_InvalidRating_ReturnsNull() throws Exception {
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"restaurantID\":1,\"userID\":1,\"rating\":15,\"comments\":\"Too high rating!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(reviewService, never()).createReview(any(Review.class));
    }

    @Test
    void getReviewById_ExistingId_ReturnsReview() throws Exception {
        Review review = new Review(1, 1, 1, 9, "Amazing food!");
        when(reviewService.getReviewById(1)).thenReturn(Optional.of(review));

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(9))
                .andExpect(jsonPath("$.comments").value("Amazing food!"));

        verify(reviewService, times(1)).getReviewById(1);
    }

    @Test
    void getReviewById_NonExistingId_ReturnsNotFound() throws Exception {
        when(reviewService.getReviewById(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reviews/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(reviewService, times(1)).getReviewById(2);
    }

    @Test
    void getAllReviews_ReturnsListOfReviews() throws Exception {
        Review review1 = new Review(1, 1, 1, 9, "Amazing food!");
        Review review2 = new Review(2, 1, 2, 7, "Good experience!");
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(9))
                .andExpect(jsonPath("$[1].rating").value(7));

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void deleteReview_ExistingId_ReturnsOk() throws Exception {
        doNothing().when(reviewService).deleteReview(1);

        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).deleteReview(1);
    }
}
