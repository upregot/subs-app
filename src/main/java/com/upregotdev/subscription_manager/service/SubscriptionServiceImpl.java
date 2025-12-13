package com.upregotdev.subscription_manager.service;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse;
import com.upregotdev.subscription_manager.entities.Subscription;
import com.upregotdev.subscription_manager.entities.User;
import com.upregotdev.subscription_manager.repository.SubscriptionRepository;
import com.upregotdev.subscription_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    // --- ERROR CORREGIDO: Se eliminó 'private final SubscriptionService subscriptionService;' ---

    @Override
    public SubscriptionResponse createSubscription(SubscriptionRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Subscription sub = new Subscription();
        sub.setName(request.getName());
        sub.setPrice(request.getPrice());
        sub.setCurrency(request.getCurrency());
        sub.setFrequency(request.getFrequency());
        sub.setStartDate(request.getStartDate());
        sub.setNextBillingDate(request.getNextBillingDate());
        sub.setLogoUrl(request.getLogoUrl());
        sub.setUser(user);

        Subscription savedSub = subscriptionRepository.save(sub);
        return mapToDto(savedSub);
    }

    @Override
    public List<SubscriptionResponse> getMySubscriptions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());

        return subscriptions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubscription(Long id, String username) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        if (!subscription.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso para eliminar esta suscripción");
        }

        subscriptionRepository.delete(subscription);
    }

    // --- ERROR CORREGIDO: Se eliminó el método @DeleteMapping que pertenece al Controller ---

    // Método auxiliar privado
    private SubscriptionResponse mapToDto(Subscription sub) {
        return SubscriptionResponse.builder()
                .id(sub.getId())
                .name(sub.getName())
                .price(sub.getPrice())
                .currency(sub.getCurrency())
                .frequency(sub.getFrequency().name())
                .startDate(sub.getStartDate())
                .nextBillingDate(sub.getNextBillingDate())
                .ownerName(sub.getUser().getUsername())
                .build();
    }
}