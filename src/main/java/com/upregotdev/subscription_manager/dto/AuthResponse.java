package com.upregotdev.subscription_manager.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token; // Aquí enviaremos el JWT
}