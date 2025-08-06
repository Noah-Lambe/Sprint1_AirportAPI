package com.keyin.airportapi.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public RegistrationService(UserRepository repo,
                               PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    @Transactional
    public User register(RegisterRequest req) {
        if (repo.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setRole(req.getRole() == null ? "ROLE_USER" : req.getRole());
        return repo.save(u);
    }
}