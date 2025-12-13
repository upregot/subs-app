package com.upregotdev.subscription_manager.controller;

import com.upregotdev.subscription_manager.dto.AuthResponse;
import com.upregotdev.subscription_manager.dto.LoginRequest;
import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.config.security.JwtService;
import com.upregotdev.subscription_manager.repository.UserRepository;
import com.upregotdev.subscription_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager; // <--- Inyectamos esto
    private final JwtService jwtService; // <--- Inyectamos la fábrica de tokens
    private final UserRepository userRepository;

    // Endpoint de Registro (Ya lo tenías)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // NUEVO: Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Esto valida usuario y contraseña automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Si pasó la línea anterior, el usuario es correcto. Lo buscamos en la BD.
        // (Usamos el repositorio directamente o un servicio, aquí lo hago directo por simplicidad)
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // 3. Generamos el Token JWT
        var jwtToken = jwtService.generateToken(user);

        // 4. Lo devolvemos al frontend
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}