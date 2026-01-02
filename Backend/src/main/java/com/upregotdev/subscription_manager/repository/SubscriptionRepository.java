package com.upregotdev.subscription_manager.repository;

import com.upregotdev.subscription_manager.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Buscar todas las suscripciones de un usuario específico
    List<Subscription> findByUser_Id(Long userId);

    // List<Subscription> findByNextBillingDateBefore(LocalDate date);
    List<Subscription> findByNextBillingDate(LocalDate date);

}