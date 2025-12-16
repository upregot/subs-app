package com.upregotdev.subscription_manager.services;

import com.upregotdev.subscription_manager.entities.Subscription;
import com.upregotdev.subscription_manager.repository.SubscriptionRepository;
import com.upregotdev.subscription_manager.services.external.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;

    @Value("${app.notifications.days-before}") // Leemos el '3' del properties
    private int daysBefore;

    // Cron: Se ejecuta todos los días a las 9:00 AM
    // Formato: seg min hora dia mes dia_semana
    @Scheduled(cron = "0 0 9 * * *")
    @Transactional(readOnly = true)
    public void checkUpcomingRenewals() {
        LocalDate targetDate = LocalDate.now().plusDays(daysBefore);

        log.info("Iniciando escaneo de suscripciones que vencen el: {}", targetDate);

        List<Subscription> subscriptions = subscriptionRepository.findByNextBillingDate(targetDate);

        if (subscriptions.isEmpty()) {
            log.info("No se encontraron vencimientos para notificar.");
            return;
        }

        log.info("Se encontraron {} suscripciones por vencer.", subscriptions.size());

        for (Subscription sub : subscriptions) {
            sendAlert(sub);
        }
    }

    private void sendAlert(Subscription sub) {
        String userEmail = sub.getUser().getEmail();
        String subject = "⚠️ Recordatorio: " + sub.getName() + " se renueva pronto";

        // Un HTML simple pero limpio para el portafolio
        String htmlContent = String.format("""
            <div style="font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                <h2 style="color: #d9534f;">Recordatorio de Pago</h2>
                <p>Hola <strong>%s</strong>,</p>
                <p>Tu suscripción a <strong>%s</strong> se renovará automáticamente en 3 días (%s).</p>
                <p style="font-size: 18px;">Monto a pagar: <strong>%s %s</strong></p>
                <hr>
                <p style="font-size: 12px; color: #777;">Si ya cancelaste este servicio, ignora este mensaje.</p>
            </div>
            """,
                sub.getUser().getUsername(),
                sub.getName(),
                sub.getNextBillingDate(),
                sub.getPrice(),
                sub.getCurrency()
        );

        emailService.sendHtmlEmail(userEmail, subject, htmlContent);
    }
}