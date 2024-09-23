package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.user.User;
import com.api.gestor_pedidos_telu.dto.AuthenticationDTO;
import com.api.gestor_pedidos_telu.dto.LoginResponseDTO;
import com.api.gestor_pedidos_telu.dto.RegisterDTO;
import com.api.gestor_pedidos_telu.infra.exception.UserAlreadyExistsException;
import com.api.gestor_pedidos_telu.infra.security.TokenService;
import com.api.gestor_pedidos_telu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO login(AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            String token = tokenService.generateToken((User) auth.getPrincipal());
            return new LoginResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Usuário inexistente ou senha inválida") {
            };
        }
    }

    public void register(RegisterDTO data) {
        if (userRepository.findByLogin(data.login()).isPresent()) {
            throw new UserAlreadyExistsException("Usuário já existe");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        userRepository.save(newUser);
    }

}
