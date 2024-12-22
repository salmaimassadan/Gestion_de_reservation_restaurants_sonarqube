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
    void getUserById() {
        // Arrange
        User user = new User(1, "John", "john@example.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertEquals("john@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(1);  // Verify findById was called once
    }

    @Test
    void getAllUsers() {
        // Arrange
        User user1 = new User(1, "John", "john@example.com");
        User user2 = new User(2, "Jane", "jane@example.com");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals("John", users.get(0).getName());
        assertEquals("Jane", users.get(1).getName());
        verify(userRepository, times(1)).findAll();  // Verify findAll was called once
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
