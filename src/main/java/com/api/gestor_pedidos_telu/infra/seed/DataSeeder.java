package com.api.gestor_pedidos_telu.infra.seed;

import com.api.gestor_pedidos_telu.domain.user.User;
import com.api.gestor_pedidos_telu.domain.user.UserRole;
import com.api.gestor_pedidos_telu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public DataSeeder(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setLogin(adminEmail);
                user.setPassword(passwordEncoder.encode(adminPassword));
                user.setRole(UserRole.ADMIN);

                userRepository.save(user);
            }
        };
    }
}
