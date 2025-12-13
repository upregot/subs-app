package com.upregotdev.subscription_manager.controller;

import com.upregotdev.subscription_manager.dto.SubscriptionRequest;
import com.upregotdev.subscription_manager.dto.SubscriptionResponse; // <--- Importante: Usamos el DTO
import com.upregotdev.subscription_manager.service.SubscriptionService;
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
    // CAMBIO: Ahora devuelve ResponseEntity<SubscriptionResponse>
    public ResponseEntity<SubscriptionResponse> create(@RequestBody SubscriptionRequest request, Principal principal) {

        // El servicio ya nos devuelve el DTO limpio (sin password)
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
    // En SubscriptionController.java

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        subscriptionService.deleteSubscription(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}