package com.example.JEE.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String username;  // Representing the user's name
    private String email;
    private String phone;

    public User(int i, String john, String mail) {
    }

    // Lombok automatically generates getter, setter, and other methods for the fields above

    // Custom method to get name, in case you want to abstract away the username field.
    public String getName() {
        return username;
    }

    public void createReservation() {
        // Placeholder logic to create a reservation
    }
}
