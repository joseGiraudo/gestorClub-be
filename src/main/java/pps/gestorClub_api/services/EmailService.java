package pps.gestorClub_api.services;

public interface EmailService {

    void sendEmail(String email);

    void sendWelcomeEmail(String sendTo, String memberName);
}
