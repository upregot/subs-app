package com.upregotdev.subscription_manager.controller;

import com.upregotdev.subscription_manager.dto.AuthResponse;
import com.upregotdev.subscription_manager.dto.LoginRequest;
import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.config.security.JwtService;
import com.upregotdev.subscription_manager.repository.UserRepository;
import com.upregotdev.subscription_manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado exitosamente"));
    }

    // NUEVO: Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );


        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}