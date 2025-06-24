package com.keyin.airportapi.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepo;

    public List<Passenger> getAllPassengers() {
        return passengerRepo.findAll();
    }

    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepo.findById(id);
    }

    public Passenger savePassenger(Passenger passenger) {
        return passengerRepo.save(passenger);
    }

    public void deletePassenger(Long id) {
        passengerRepo.deleteById(id);
    }
}
