package com.api.gestor_pedidos_telu.controller;

import com.api.gestor_pedidos_telu.dto.AuthenticationDTO;
import com.api.gestor_pedidos_telu.dto.LoginResponseDTO;
import com.api.gestor_pedidos_telu.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO data) {
        LoginResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthenticationDTO data) {
        authenticationService.register(data);
        return ResponseEntity.ok().build();
    }

}
