package com.upregotdev.subscription_manager.dto;

import com.upregotdev.subscription_manager.entities.Frequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubscriptionRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal price;

    private String currency; // "USD", "ARS"

    @NotNull(message = "La frecuencia es obligatoria")
    private Frequency frequency; // MONTHLY, YEARLY

    private LocalDate startDate;
    private LocalDate nextBillingDate;
    private String logoUrl;
}