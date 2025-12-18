package com.upregotdev.subscription_manager.services.implementations;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse;
import com.upregotdev.subscription_manager.entities.Subscription;
import com.upregotdev.subscription_manager.entities.User;
import com.upregotdev.subscription_manager.exception.BusinessAccessDeniedException;
import com.upregotdev.subscription_manager.exception.ResourceNotFoundException;
import com.upregotdev.subscription_manager.repository.SubscriptionRepository;
import com.upregotdev.subscription_manager.repository.UserRepository;
import com.upregotdev.subscription_manager.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional // Asegura integridad en la escritura
    public SubscriptionResponse createSubscription(SubscriptionRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Subscription sub = new Subscription();
        sub.setName(request.getName());
        sub.setPrice(request.getPrice());
        sub.setCurrency(request.getCurrency());
        sub.setFrequency(request.getFrequency());
        sub.setLogoUrl(request.getLogoUrl());
        sub.setUser(user);

        // LÓGICA 1: Manejo de Fecha de Inicio (Si es nula, es HOY)
        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        sub.setStartDate(startDate);

        // LÓGICA 2: Cálculo automático de próxima fecha (Backend authority)
        calculateNextBillingDate(sub);

        Subscription savedSub = subscriptionRepository.save(sub);
        return mapToDto(savedSub);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getMySubscriptions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Nota: Asegúrate que tu repositorio tenga este método.
        // Si usas JPA estándar suele ser findByUser(user) o findByUserId(id)
        List<Subscription> subscriptions = subscriptionRepository.findByUser_Id(user.getId());

        return subscriptions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalMensual(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Subscription> subs = subscriptionRepository.findByUser_Id(user.getId());

        // LÓGICA 3: Normalización de Precios (Estadística real)
        return subs.stream()
                .map(this::normalizeToMonthlyCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void deleteSubscription(Long id, String username) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripción no encontrada con ID: " + id));

        // Seguridad: IDOR check
        if (!subscription.getUser().getUsername().equals(username)) {
            throw new BusinessAccessDeniedException("No tienes permiso para eliminar esta suscripción");
        }

        subscriptionRepository.delete(subscription);
    }

    @Override
    @Transactional
    public SubscriptionResponse updateSubscription(Long id, SubscriptionRequest request, String username) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripción no encontrada"));

        if (!subscription.getUser().getUsername().equals(username)) {
            throw new BusinessAccessDeniedException("No es tu suscripción");
        }

        subscription.setName(request.getName());
        subscription.setPrice(request.getPrice());
        subscription.setLogoUrl(request.getLogoUrl());
        subscription.setCurrency(request.getCurrency());

        // CORRECCIÓN 1: Detección de cambio de frecuencia
        boolean frequencyChanged = request.getFrequency() != null && request.getFrequency() != subscription.getFrequency();

        if (frequencyChanged) {
            subscription.setFrequency(request.getFrequency());
            // CORRECCIÓN 2: Si cambia la frecuencia, recalculamos desde HOY, no desde el pasado
            calculateNextBillingDate(subscription, LocalDate.now());
        } else if (request.getStartDate() != null && !request.getStartDate().equals(subscription.getStartDate())) {
            // Si solo cambia la fecha de inicio (corrección manual), usamos esa nueva fecha
            subscription.setStartDate(request.getStartDate());
            calculateNextBillingDate(subscription, request.getStartDate());
        }

        return mapToDto(subscriptionRepository.save(subscription));
    }

    // Método auxiliar sobrecargado para facilitar el uso
    private void calculateNextBillingDate(Subscription subscription) {
        calculateNextBillingDate(subscription, subscription.getStartDate());
    }

    private void calculateNextBillingDate(Subscription subscription, LocalDate baseDate) {
        if (subscription.getFrequency() == null) return;

        switch (subscription.getFrequency()) {
            case ONE_TIME -> subscription.setNextBillingDate(null); // CORRECCIÓN 3: Null para pago único
            case MONTHLY -> subscription.setNextBillingDate(baseDate.plusMonths(1));
            case YEARLY -> subscription.setNextBillingDate(baseDate.plusYears(1));
            case WEEKLY -> subscription.setNextBillingDate(baseDate.plusWeeks(1));
            case QUARTERLY -> subscription.setNextBillingDate(baseDate.plusMonths(3));
            default -> subscription.setNextBillingDate(baseDate.plusMonths(1));
        }
    }

    private BigDecimal normalizeToMonthlyCost(Subscription sub) {
        if (sub.getPrice() == null) return BigDecimal.ZERO;

        switch (sub.getFrequency()) {
            case YEARLY:
                // Divide por 12 meses
                return sub.getPrice().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
            case QUARTERLY:
                // Divide por 3 meses
                return sub.getPrice().divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
            case WEEKLY:
                // Multiplica por semanas promedio en un mes (4.345)
                return sub.getPrice().multiply(BigDecimal.valueOf(4.345)).setScale(2, RoundingMode.HALF_UP);
            case MONTHLY:
            default:
                return sub.getPrice();
        }
    }

    private SubscriptionResponse mapToDto(Subscription sub) {
        return SubscriptionResponse.builder()
                .id(sub.getId())
                .name(sub.getName())
                .price(sub.getPrice())
                .currency(sub.getCurrency())
                .frequency(sub.getFrequency() != null ? sub.getFrequency().name() : "MONTHLY")
                .startDate(sub.getStartDate())
                .nextBillingDate(sub.getNextBillingDate())
                .ownerName(sub.getUser().getUsername())
                .logoUrl(sub.getLogoUrl()) // Agregado para que no se pierda en la respuesta
                .build();
    }
}