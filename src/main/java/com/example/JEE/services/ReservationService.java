package com.example.JEE.services;

import com.example.JEE.entities.Reservation;
import com.example.JEE.entities.Status;
import com.example.JEE.repositories.ReservationRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(Status.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Confirmation de réservation");
            message.setFrom("Saadkhallouki10@gmail.com");
            message.setTo(reservation.getEmail());
            message.setText("Votre réservation code : " + savedReservation.getReservationID() + "\n" +
                    "Statut : En attente de confirmation");

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Erreur d'envoi d'email: " + e.getMessage());
            e.printStackTrace();
        }

        return savedReservation;
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    public Reservation validateReservation(int id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        Reservation reservation = reservationOpt.orElseThrow(() ->
                new OpenApiResourceNotFoundException("Reservation not found for ID: " + id));

        reservation.setStatus(Status.RESERVED);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Confirmation de votre réservation");
        message.setFrom("Saadkhallouki10@gmail.com");
        message.setTo(reservation.getEmail()); // Utiliser l'email du client
        message.setText(
                "Bonjour,\n\n" +
                        "Votre réservation a été confirmée!\n" +
                        "Numéro de réservation : " + reservation.getReservationID() + "\n" +
                        "Status : " + reservation.getStatus() + "\n" +
                        "Nous avons hâte de vous accueillir!\n\n" +
                        "Cordialement,\n" +
                        "L'équipe du restaurant"
        );

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Erreur d'envoi d'email: " + e.getMessage());
            e.printStackTrace();
        }

        return reservationRepository.save(reservation);
    }

    // Ajouter une méthode pour l'email de refus
    public void sendRefusalEmail(int id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Réservation non disponible");
            message.setFrom("Saadkhallouki10@gmail.com");
            message.setTo(reservation.getEmail()); // Utiliser l'email du client
            message.setText(
                    "Bonjour,\n\n" +
                            "Nous sommes désolés de vous informer que votre demande de réservation " +
                            "(Numéro : " + reservation.getReservationID() + ") " +
                            "ne peut pas être confirmée pour le moment.\n\n" +
                            "N'hésitez pas à nous contacter pour plus d'informations ou pour faire " +
                            "une nouvelle réservation à une autre date.\n\n" +
                            "Cordialement,\n" +
                            "L'équipe du restaurant"
            );

            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                System.out.println("Erreur d'envoi d'email: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

