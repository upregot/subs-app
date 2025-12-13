package com.upregotdev.subscription_manager.service;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse; // Importar DTO
import java.util.List;

public interface SubscriptionService {

    // Cambiamos 'Subscription' por 'SubscriptionResponse' aquí también
    SubscriptionResponse createSubscription(SubscriptionRequest request, String username);

    List<SubscriptionResponse> getMySubscriptions(String username);

    void deleteSubscription(Long id, String username);
}