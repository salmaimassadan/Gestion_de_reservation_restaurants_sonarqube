package com.example.JEE.services;

import com.example.JEE.entities.Reservation;
import com.example.JEE.entities.Status;
import com.example.JEE.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void createReservation_Success() {
        // Arrange
        Reservation reservation = new Reservation(1, "user@example.com", Status.PENDING);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation createdReservation = reservationService.createReservation(reservation);

        // Assert
        assertEquals(Status.PENDING, createdReservation.getStatus());
        verify(reservationRepository, times(1)).save(reservation);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }




    @Test
    void getReservationById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(reservationRepository.findById(2)).thenReturn(Optional.empty());

        // Act
        Optional<Reservation> result = reservationService.getReservationById(2);

        // Assert
        assertFalse(result.isPresent(), "Reservation should not be present");
        verify(reservationRepository, times(1)).findById(2);
    }

    @Test
    void getAllReservations_ReturnsReservations() {
        // Arrange
        Reservation reservation1 = new Reservation(1, "user1@example.com", Status.PENDING);
        Reservation reservation2 = new Reservation(2, "user2@example.com", Status.RESERVED);
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        // Act
        List<Reservation> reservations = reservationService.getAllReservations();

        // Assert
        assertEquals(2, reservations.size(), "The number of reservations does not match");
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void deleteReservation_CallsRepositoryDelete() {
        // Act
        reservationService.deleteReservation(1);

        // Assert
        verify(reservationRepository, times(1)).deleteById(1);
    }

    @Test
    void validateReservation_ExistingId_SendsConfirmationEmail() {
        // Arrange
        Reservation reservation = new Reservation(1, "user@example.com", Status.PENDING);
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation validatedReservation = reservationService.validateReservation(1);

        // Assert
        assertEquals(Status.RESERVED, validatedReservation.getStatus(), "Status should be RESERVED");
        verify(reservationRepository, times(1)).findById(1);
        verify(reservationRepository, times(1)).save(reservation);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void validateReservation_NonExistingId_ThrowsException() {
        // Arrange
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(OpenApiResourceNotFoundException.class, () -> reservationService.validateReservation(1));
        assertEquals("Reservation not found for ID: 1", exception.getMessage(), "Exception message does not match");
        verify(reservationRepository, times(1)).findById(1);
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendRefusalEmail_ExistingReservation_SendsEmail() {
        // Arrange
        Reservation reservation = new Reservation(1, "user@example.com", Status.PENDING);
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        // Act
        reservationService.sendRefusalEmail(1);

        // Assert
        verify(reservationRepository, times(1)).findById(1);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendRefusalEmail_NonExistingReservation_DoesNothing() {
        // Arrange
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        reservationService.sendRefusalEmail(1);

        // Assert
        verify(reservationRepository, times(1)).findById(1);
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}
