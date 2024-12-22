package com.example.JEE.controllers;

import com.example.JEE.entities.Reservation;
import com.example.JEE.entities.Status;
import com.example.JEE.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

    @Test
    void createReservation() {
        Reservation reservation = new Reservation(1, 2, 3, 4, "test@example.com", LocalDateTime.now(), Status.PENDING);

        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationController.createReservation(reservation);

        assertNotNull(createdReservation);
        assertEquals(1, createdReservation.getReservationID());
        assertEquals("test@example.com", createdReservation.getEmail());
        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void getReservationById() {
        Reservation reservation = new Reservation(1, 2, 3, 4, "test@example.com", LocalDateTime.now(), Status.PENDING);

        when(reservationService.getReservationById(1)).thenReturn(Optional.of(reservation));

        Optional<Reservation> retrievedReservation = reservationController.getReservationById(1);

        assertTrue(retrievedReservation.isPresent());
        assertEquals(1, retrievedReservation.get().getReservationID());
        assertEquals("test@example.com", retrievedReservation.get().getEmail());
        verify(reservationService, times(1)).getReservationById(1);
    }

    @Test
    void validateReservation() {
        Reservation reservation = new Reservation(1, 2, 3, 4, "test@example.com", LocalDateTime.now(), Status.CONFIRMED);

        when(reservationService.validateReservation(1)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.validateReservation(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReservationID());
        assertEquals(Status.CONFIRMED, response.getBody().getStatus());
        verify(reservationService, times(1)).validateReservation(1);
    }

    @Test
    void getAllReservations() {
        Reservation reservation1 = new Reservation(1, 2, 3, 4, "email1@example.com", LocalDateTime.now(), Status.PENDING);
        Reservation reservation2 = new Reservation(2, 5, 6, 7, "email2@example.com", LocalDateTime.now(), Status.CONFIRMED);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        List<Reservation> allReservations = reservationController.getAllReservations();

        assertNotNull(allReservations);
        assertEquals(2, allReservations.size());
        assertEquals("email1@example.com", allReservations.get(0).getEmail());
        assertEquals("email2@example.com", allReservations.get(1).getEmail());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void deleteReservation() {
        doNothing().when(reservationService).sendRefusalEmail(1);
        doNothing().when(reservationService).deleteReservation(1);

        ResponseEntity<Void> response = reservationController.deleteReservation(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reservationService, times(1)).sendRefusalEmail(1);
        verify(reservationService, times(1)).deleteReservation(1);
    }
}
