package pps.gestorClub_api.services;

import pps.gestorClub_api.models.Payment;

import java.util.List;

public interface EmailService {

    void sendEmail(String email);

    void sendWelcomeEmail(String sendTo, String memberName);

    void sendPaymentsEmail(String sendTo, String memberName, List<Payment> payments);
}
