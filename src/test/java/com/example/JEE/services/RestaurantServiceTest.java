package com.example.JEE.services;

import com.example.JEE.entities.Restaurant;
import com.example.JEE.repositories.RestaurantRepository;
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

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;  // Mock the RestaurantRepository

    @InjectMocks
    private RestaurantService restaurantService;  // Inject the mock repository into the service

    // Set up method to initialize mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }







    @Test
    void deleteRestaurant() {
        // Arrange
        int restaurantId = 1;
        doNothing().when(restaurantRepository).deleteById(restaurantId);

        // Act
        restaurantService.deleteRestaurant(restaurantId);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(restaurantId);  // Verify deleteById was called once
    }
}
