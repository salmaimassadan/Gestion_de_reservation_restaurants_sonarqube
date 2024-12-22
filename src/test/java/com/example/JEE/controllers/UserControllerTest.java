package com.example.JEE.controllers;

import com.example.JEE.entities.User;
import com.example.JEE.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User(1, "John Doe", "johndoe@example.com", "1234567890");
    }

    @Test
    void createUser() {
        when(userService.createUser(mockUser)).thenReturn(mockUser);

        User createdUser = userController.createUser(mockUser);

        assertEquals(mockUser, createdUser);
        verify(userService, times(1)).createUser(mockUser);
    }

    @Test
    void getUserById() {
        when(userService.getUserById(1)).thenReturn(Optional.of(mockUser));

        Optional<User> retrievedUser = userController.getUserById(1);

        assertEquals(Optional.of(mockUser), retrievedUser);
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(mockUser, new User(2, "Jane Doe", "janedoe@example.com", "0987654321"));
        when(userService.getAllUsers()).thenReturn(users);

        List<User> retrievedUsers = userController.getAllUsers();

        assertEquals(users, retrievedUsers);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void deleteUser() {
        doNothing().when(userService).deleteUser(1);

        userController.deleteUser(1);

        verify(userService, times(1)).deleteUser(1);
    }
}
