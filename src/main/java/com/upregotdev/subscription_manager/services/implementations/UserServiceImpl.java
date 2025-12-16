package com.upregotdev.subscription_manager.services.implementations;
import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.entities.Role;
import com.upregotdev.subscription_manager.entities.User;
import com.upregotdev.subscription_manager.repository.UserRepository;
import com.upregotdev.subscription_manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) { // CORRECCIÓN 4: Void, no devuelve User
        // Validación rápida (UX)
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe"); // Idealmente usa una excepción personalizada
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // CORRECCIÓN 5: Captura la condición de carrera real
            throw new RuntimeException("El usuario o email ya existe (Error de integridad)");
        }
        return user;
    }
}