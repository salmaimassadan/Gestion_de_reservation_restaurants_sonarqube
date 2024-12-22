package com.example.JEE.controllers;

import com.example.JEE.entities.Restaurant;
import com.example.JEE.services.RestaurantService;
import com.example.JEE.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private StorageService storageService;

    @PostMapping
    public Restaurant createRestaurant(@RequestParam("Name") String name,@RequestParam("Localisation")
                                        String localisation, @RequestParam("file") MultipartFile file )
            throws IOException {
        Restaurant restaurant=new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(localisation);
        restaurant.setImgUrl(storageService.uploadImageToFileSystem(file));
        return restaurantService.createRestaurant(restaurant);
    }


    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable int id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(
            @PathVariable int id,
            @RequestParam("Name") String name,
            @RequestParam("Localisation") String localisation,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Restaurant restaurant = restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setName(name);
        restaurant.setLocation(localisation);

        if (file != null && !file.isEmpty()) {
            restaurant.setImgUrl(storageService.uploadImageToFileSystem(file));
        }

        return restaurantService.createRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable int id) {
        restaurantService.deleteRestaurant(id);
    }
}
