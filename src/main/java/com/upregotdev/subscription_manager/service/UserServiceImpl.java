package com.upregotdev.subscription_manager.service;
import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.entities.Role;
import com.upregotdev.subscription_manager.entities.User;
import com.upregotdev.subscription_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Inyecta automáticamente los repositorios (Constructor Injection)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) {
        // 1. Validar que el usuario no exista
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Crear la Entidad a partir del DTO
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // 3. ENCRIPTAR la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Asignar rol por defecto
        user.setRole(Role.USER);

        // 5. Guardar en BD
        return userRepository.save(user);
    }
}