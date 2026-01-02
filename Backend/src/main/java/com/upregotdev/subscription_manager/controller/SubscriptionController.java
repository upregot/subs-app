package com.upregotdev.subscription_manager.controller;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse; // <--- Importante: Usamos el DTO
import com.upregotdev.subscription_manager.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // Crear una suscripción
    @PostMapping
    public ResponseEntity<SubscriptionResponse> create(@Valid @RequestBody SubscriptionRequest request, Principal principal) {

        SubscriptionResponse newSub = subscriptionService.createSubscription(request, principal.getName());
        return ResponseEntity.ok(newSub);
    }

    // Ver MIS suscripciones
    @GetMapping
    // CAMBIO: Devuelve List<SubscriptionResponse>
    public ResponseEntity<List<SubscriptionResponse>> getMySubscriptions(Principal principal) {

        List<SubscriptionResponse> misSuscripciones = subscriptionService.getMySubscriptions(principal.getName());
        return ResponseEntity.ok(misSuscripciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        subscriptionService.deleteSubscription(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody SubscriptionRequest request,
                                                       Principal principal) {

        SubscriptionResponse response = subscriptionService.updateSubscription(id, request, principal.getName());
        return ResponseEntity.ok(response);
    }
}