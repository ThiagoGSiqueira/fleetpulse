package com.fleetpulse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fleetpulse.domain.User;
import com.fleetpulse.domain.UserRole;
import com.fleetpulse.repository.UserRepository;

/**
 * Cria o primeiro ADMIN ao subir a aplicação.
 * Como /users/register exige um ADMIN logado, sem isso ninguém conseguiria entrar.
 */
@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                @Value("${app.admin.email}") String email,
                                @Value("${app.admin.password}") String password) {
        return args -> {
            // só cria se ainda não existir
            if (!userRepository.existsByEmail(email)) {
                userRepository.save(new User("Admin", email, passwordEncoder.encode(password), UserRole.ROLE_ADMIN));
            }
        };
    }
}
