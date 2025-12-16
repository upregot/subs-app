package com.upregotdev.subscription_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- 1. Importar esto
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; // <--- 2. Importar esto
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // --- CORRECCIÓN AQUÍ ---
    @OneToMany(mappedBy = "user")
    @ToString.Exclude // <--- VITAL: Evita error LazyInitialization y bucles en logs
    // @JsonIgnore    // <--- OPCIONAL: Descomenta esto si NO quieres que Swagger muestre la lista de suscripciones al ver el usuario.
    private List<Subscription> subscriptions;

    // --- MÉTODOS DE USER DETAILS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}