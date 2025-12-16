package com.upregotdev.subscription_manager.repository;

import com.upregotdev.subscription_manager.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Buscar todas las suscripciones de un usuario específico
    // SQL: SELECT * FROM subscriptions WHERE user_id = ?
    List<Subscription> findByUserId(Long userId);

    // Opcional: Para el futuro, buscar las que vencen antes de X fecha (para los emails)
    // List<Subscription> findByNextBillingDateBefore(LocalDate date);
    List<Subscription> findByNextBillingDate(LocalDate date);

}