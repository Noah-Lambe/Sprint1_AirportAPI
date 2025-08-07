package com.keyin.airportapi.user;

import com.keyin.airportapi.city.City;
import com.keyin.airportapi.city.CityRepository;
import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        //Create & save User
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setRole(req.getRole() == null ? "ROLE_USER" : req.getRole());
        u = userRepository.save(u);

        //Create & save Passenger with full profile
        Passenger p = new Passenger();
        p.setUserId(u.getId());
        p.setFirstName(req.getFirstName());
        p.setLastName(req.getLastName());
        p.setPhoneNumber(req.getPhoneNumber());
        if (req.getCityId() != null) {
            City city = cityRepository.findById(req.getCityId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid cityId"));
            p.setCity(city);
        }
        passengerRepository.save(p);

        return u;
    }
}
