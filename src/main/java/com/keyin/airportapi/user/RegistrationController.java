package com.keyin.airportapi.user;

import com.keyin.airportapi.user.RegisterRequest;
import com.keyin.airportapi.user.RegisterResponse;
import com.keyin.airportapi.user.RegistrationService;
import com.keyin.airportapi.user.User;
import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    PassengerRepository passengerRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
        User u = registrationService.register(req);
        Passenger p = passengerRepository.findByUserId(u.getId()).get();
        RegisterResponse resp = new RegisterResponse(
                u.getId(),
                u.getUsername(),
                p.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
