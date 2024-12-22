package com.example.JEE.controllers;

import com.example.JEE.entities.Restaurant;
import com.example.JEE.services.RestaurantService;
import com.example.JEE.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-image.jpg");
        when(storageService.uploadImageToFileSystem(file)).thenReturn("uploads/test-image.jpg");

        Restaurant restaurant = new Restaurant(1, "Test Restaurant", "Test Location", "uploads/test-image.jpg", null);

        when(restaurantService.createRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant createdRestaurant = restaurantController.createRestaurant(
                "Test Restaurant", "Test Location", file);

        assertNotNull(createdRestaurant);
        assertEquals("Test Restaurant", createdRestaurant.getName());
        assertEquals("Test Location", createdRestaurant.getLocation());
        assertEquals("uploads/test-image.jpg", createdRestaurant.getImgUrl());
        verify(storageService, times(1)).uploadImageToFileSystem(file);
        verify(restaurantService, times(1)).createRestaurant(any(Restaurant.class));
    }

    @Test
    void getRestaurantById() {
        Restaurant restaurant = new Restaurant(1, "Test Restaurant", "Test Location", "uploads/test-image.jpg", null);
        when(restaurantService.getRestaurantById(1)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> retrievedRestaurant = restaurantController.getRestaurantById(1);

        assertTrue(retrievedRestaurant.isPresent());
        assertEquals("Test Restaurant", retrievedRestaurant.get().getName());
        verify(restaurantService, times(1)).getRestaurantById(1);
    }

    @Test
    void getAllRestaurants() {
        Restaurant restaurant1 = new Restaurant(1, "Restaurant 1", "Location 1", "uploads/image1.jpg", null);
        Restaurant restaurant2 = new Restaurant(2, "Restaurant 2", "Location 2", "uploads/image2.jpg", null);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        List<Restaurant> allRestaurants = restaurantController.getAllRestaurants();

        assertNotNull(allRestaurants);
        assertEquals(2, allRestaurants.size());
        assertEquals("Restaurant 1", allRestaurants.get(0).getName());
        assertEquals("Restaurant 2", allRestaurants.get(1).getName());
        verify(restaurantService, times(1)).getAllRestaurants();
    }

    @Test
    void updateRestaurant() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("updated-image.jpg");
        when(storageService.uploadImageToFileSystem(file)).thenReturn("uploads/updated-image.jpg");

        Restaurant restaurant = new Restaurant(1, "Old Name", "Old Location", "uploads/old-image.jpg", null);
        when(restaurantService.getRestaurantById(1)).thenReturn(Optional.of(restaurant));
        when(restaurantService.createRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant updatedRestaurant = restaurantController.updateRestaurant(
                1, "Updated Name", "Updated Location", file);

        assertNotNull(updatedRestaurant);
        assertEquals("Updated Name", updatedRestaurant.getName());
        assertEquals("Updated Location", updatedRestaurant.getLocation());
        assertEquals("uploads/updated-image.jpg", updatedRestaurant.getImgUrl());
        verify(storageService, times(1)).uploadImageToFileSystem(file);
        verify(restaurantService, times(1)).createRestaurant(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant() {
        doNothing().when(restaurantService).deleteRestaurant(1);

        restaurantController.deleteRestaurant(1);

        verify(restaurantService, times(1)).deleteRestaurant(1);
    }
}
