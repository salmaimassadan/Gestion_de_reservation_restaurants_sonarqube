package com.example.JEE.services;

import com.example.JEE.entities.User;
import com.example.JEE.repositories.UserRepository;
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

class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Mock the UserRepository

    @InjectMocks
    private UserService userService;  // Inject the mock repository into the service

    // Set up method to initialize mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }





    @Test
    void deleteUser() {
        // Arrange
        int userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);  // Verify deleteById was called once
    }
}
