package com.api.gestor_pedidos_telu.repository;

import com.api.gestor_pedidos_telu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<UserDetails> findByLogin(String login);
}
