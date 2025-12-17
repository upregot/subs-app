package com.upregotdev.subscription_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Ej: Netflix

    @Column(nullable = false)
    private BigDecimal price; // Siempre usa BigDecimal para dinero, nunca Double

    @Column(length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    private Frequency frequency; // MONTHLY, YEARLY...

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "next_billing_date")
    private LocalDate nextBillingDate;

    private String logoUrl; // Para guardar la URL de la imagen del servicio

    // --- RELACIONES ---

    // Muchas Suscripciones pertenecen a UN Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // FK en la base de datos
    @ToString.Exclude // IMPORTANTE: Evita que al imprimir la suscripción se imprima el usuario y cause un bucle infinito
    @JsonIgnore
    private User user;
}