package com.api.gestor_pedidos_telu.controller;

import com.api.gestor_pedidos_telu.dto.AuthenticationDTO;
import com.api.gestor_pedidos_telu.dto.LoginResponseDTO;
import com.api.gestor_pedidos_telu.dto.RegisterDTO;
import com.api.gestor_pedidos_telu.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.api.gestor_pedidos_telu.infra.security.SecurityConfigurations.SECURITY_NAME;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth")
@SecurityRequirement(name = SECURITY_NAME)
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO data) {
        LoginResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO data) {
        authenticationService.register(data);
        return ResponseEntity.ok().build();
    }

}
