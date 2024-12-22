package com.example.JEE.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationID; // Identifiant de réservation
    private int userID;        // Identifiant de l'utilisateur
    private int restaurantID;  // Identifiant du restaurant
    private int tableID;       // Identifiant de la table
    private String email;      // Email de l'utilisateur
    private LocalDateTime reservationTime; // Date et heure de la réservation
    private Status status;     // Statut de la réservation (Enumération)

    public Reservation(int i, String mail, Status status) {
    }

    // Supprimer les méthodes inutiles ou redondantes comme setId, setName, getName, getId.
}
