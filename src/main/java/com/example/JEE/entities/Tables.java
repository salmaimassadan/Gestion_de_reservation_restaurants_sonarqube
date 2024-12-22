package com.example.JEE.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableID;
    private int capacity;
    @ManyToOne
    private Restaurant restaurant;
    private boolean isAvailable;

    public void reserveTable() {
        this.isAvailable = false;
    }

    // Getters and setters
}
