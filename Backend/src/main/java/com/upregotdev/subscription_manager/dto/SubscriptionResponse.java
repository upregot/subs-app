package com.upregotdev.subscription_manager.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String currency;
    private String frequency;
    private LocalDate startDate;
    private LocalDate nextBillingDate;
    private String ownerName; // En lugar del objeto User entero, solo mandamos el nombre
    private String logoUrl;
}