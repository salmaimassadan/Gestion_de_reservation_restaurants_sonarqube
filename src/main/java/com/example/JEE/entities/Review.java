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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;
    private int restaurantID;
    private int userID;
    private int rating;
    private String comments;

    public void setId(int i) {
    }

    public void setContent(String s) {
    }

    public int getId() {
        return reviewID;
    }

    public String getContent() {
        return comments;
    }

    // Getters and setters
}
