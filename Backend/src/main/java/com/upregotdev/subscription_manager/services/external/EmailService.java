package com.upregotdev.subscription_manager.services.external;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // Lombok para logs
public class EmailService {

    private final JavaMailSender mailSender;

    @Async // IMPORTANTE: Ejecuta esto en un hilo separado. El usuario no espera.
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indica que es HTML
            // helper.setFrom("noreply@tuapp.com"); // Opcional

            mailSender.send(message);
            log.info("Correo eenviado a: {}", to);

        } catch (MessagingException e) {
            log.error("Error enviando correo a {}: {}", to, e.getMessage());
        }
    }
}