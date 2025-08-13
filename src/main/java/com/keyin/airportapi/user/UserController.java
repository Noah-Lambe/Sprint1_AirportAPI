package com.keyin.airportapi.user;

import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserController {
    private final AuthenticationManager authManager;

    public UserController(AuthenticationManager am) {
        this.authManager = am;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> creds) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.get("username"), creds.get("password"))
        );

        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "User not found"));

        String role = user.getRole();
        boolean isUser  = "USER".equalsIgnoreCase(role)  || "ROLE_USER".equalsIgnoreCase(role);
        boolean isAdmin = "ADMIN".equalsIgnoreCase(role) || "ROLE_ADMIN".equalsIgnoreCase(role);

        Passenger p = null;
        if (isUser) {
            p = passengerRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Passenger missing"));
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("username",    user.getUsername());
        resp.put("roles",       role);
        resp.put("userId",      user.getId());
        resp.put("passengerId", p != null ? p.getId() : null);

        if (p != null) {
            resp.put("firstName",   p.getFirstName());
            resp.put("lastName",    p.getLastName());
            resp.put("phoneNumber", p.getPhoneNumber());
            resp.put("cityId",      p.getCity() != null ? p.getCity().getId() : null);
        }
        return resp;
    }


}
