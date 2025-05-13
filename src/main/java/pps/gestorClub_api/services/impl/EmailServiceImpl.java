package pps.gestorClub_api.services.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;
import pps.gestorClub_api.models.Payment;
import pps.gestorClub_api.services.EmailService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    @Override
    @SneakyThrows
    public void sendWelcomeEmail(String sendTo, String memberName) {
        // Read base html
        String rawHtml = loadHtmlFile("templates/welcome.html");

        // replace name variable
        String html = rawHtml.replace("{{memberName}}", memberName);

        // clean html with tidy
        String cleanHtml = cleanHtmlTidy(html);

        // send email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(sendTo);
        helper.setSubject("¡Ya eres Socio del CLub!");
        helper.setText(cleanHtml, true);

        mailSender.send(message);
    }

    @Override
    @SneakyThrows
    public void sendPaymentsEmail(String sendTo, String memberName, List<Payment> payments) {
        // Read base html
        String rawHtml = loadHtmlFile("templates/payments.html");

        BigDecimal total = payments.stream()
                .map(p -> p.getFeeId().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // replace name variable
        String html = rawHtml.replace("{{memberName}}", memberName)
                .replace("{{tableBody}}", generatePaymentRows(payments))
                .replace("{{total}}", "$" + total);

        // clean html with tidy
        String cleanHtml = cleanHtmlTidy(html);

        // send email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(sendTo);
        helper.setSubject("Estado de Cuentas");
        helper.setText(cleanHtml, true);

        mailSender.send(message);
    }


    private String generatePaymentRows(List<Payment> payments) {
        StringBuilder builder = new StringBuilder();
        for(Payment payment : payments) {
            builder.append("<tr>")
                    .append("<td>").append(payment.getId()).append("</td>")
                    .append("<td>").append(payment.getIssuedDate()).append("</td>")
                    .append("<td>").append(payment.getFeeId().getMonth()).append("</td>")
                    .append("<td>").append(payment.getFeeId().getYear()).append("</td>")
                    .append("<td>").append(payment.getFeeId().getAmount()).append("</td>")
                    .append("</tr>");
        }
        return builder.toString();
    }


    @SneakyThrows
    private String loadHtmlFile(String route) {
        ClassPathResource resource = new ClassPathResource(route);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private String cleanHtmlTidy(String html) {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);

        ByteArrayInputStream input = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        tidy.parse(input, output);
        return output.toString(StandardCharsets.UTF_8);
    }

}
