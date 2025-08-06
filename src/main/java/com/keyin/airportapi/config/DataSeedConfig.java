package com.keyin.airportapi.config;

import com.keyin.airportapi.user.RegisterRequest;
import com.keyin.airportapi.user.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DataSeedConfig {

    @Bean
    CommandLineRunner seedAdmin(RegistrationService reg) {
        return args -> {
            try {
                var req = new RegisterRequest();
                req.setUsername("admin");
                req.setPassword("AdminPassword1!");
                req.setRole("ROLE_ADMIN");
                reg.register(req);
                System.out.println("Created default admin user");
            } catch (IllegalArgumentException e) {
                System.out.println("Admin user already exists, skipping seeding");
            }
        };
    }
}
