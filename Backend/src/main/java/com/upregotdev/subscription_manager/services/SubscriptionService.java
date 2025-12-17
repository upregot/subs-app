package com.upregotdev.subscription_manager.services;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse; // Importar DTO

import java.math.BigDecimal;
import java.util.List;

public interface SubscriptionService {

    // Cambiamos 'Subscription' por 'SubscriptionResponse' aquí también
    SubscriptionResponse createSubscription(SubscriptionRequest request, String username);

    List<SubscriptionResponse> getMySubscriptions(String username);

    BigDecimal calcularTotalMensual(String username);

    void deleteSubscription(Long id, String username);

    SubscriptionResponse updateSubscription(Long id, SubscriptionRequest request, String username);
}