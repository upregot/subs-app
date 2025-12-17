package com.upregotdev.subscription_manager.repository;

import com.upregotdev.subscription_manager.entities.User; // Asegúrate que importe tu Entidad correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data es tan inteligente que crea la consulta SQL solo por el nombre del método:
    // SQL generado: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);

    // Para validar que no se repitan emails al registrarse
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}