package com.upregotdev.subscription_manager.entities; // Tu paquete

import com.upregotdev.subscription_manager.entities.Role;
import com.upregotdev.subscription_manager.entities.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails { // <--- ¡CAMBIO CLAVE!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Importante: username no se puede repetir
    private String username;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;

    // --- MÉTODOS DE USER DETAILS (Copia y pega esto) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte tu Enum ROLE_USER en un permiso que Spring entienda
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta nunca vence
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // La contraseña no vence
    }

    @Override
    public boolean isEnabled() {
        return true; // El usuario está habilitado
    }
}