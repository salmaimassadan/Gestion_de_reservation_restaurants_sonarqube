package com.example.JEE.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantID;
    private String name;
    private String location;
    private String ImgUrl;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Tables> tables;

    public Restaurant(int i, String theDiner, String s) {
    }

    public boolean reserveTable() {
        // Placeholder logic to reserve a table
        return true;
    }

    public String getAddress() {
        return location;
    }

    // Getters and setters
}
