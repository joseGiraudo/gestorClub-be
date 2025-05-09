package pps.gestorClub_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendEmail(String email) {

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Probando");
        mensaje.setText("Ejemplo");
        mensaje.setFrom("1999francogarcia@gmail.com");

        mailSender.send(mensaje);
    }
}
