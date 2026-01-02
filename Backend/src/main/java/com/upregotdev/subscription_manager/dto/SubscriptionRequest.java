package com.upregotdev.subscription_manager.dto;

import com.upregotdev.subscription_manager.entities.Frequency;
import jakarta.validation.constraints.*; // Importante
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubscriptionRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero") // ¡Magia!
    private BigDecimal price;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe ser un código de 3 letras (ej: USD)")
    private String currency;

    @NotNull(message = "La frecuencia es obligatoria")
    private Frequency frequency;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate startDate;

    @NotNull(message = "La próxima fecha de cobro es obligatoria")
    private LocalDate nextBillingDate;

    private String logoUrl;
}