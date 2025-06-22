package com.keyin.airportapi.aircraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    public List<Aircraft> getAllAircraft() {
        return (List<Aircraft>) aircraftRepository.findAll();
    }

    public Optional<Aircraft> getAircraftById(Long id) {
        return aircraftRepository.findById(id);
    }

    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }

    public Aircraft createAircraft(Aircraft newAircraft) {
        return aircraftRepository.save(newAircraft);
    }

    public Aircraft updateAircraft(Long id, Aircraft updatedAircraft) {
        Optional<Aircraft> existingAircraftOpt = aircraftRepository.findById(id);

        if (existingAircraftOpt.isPresent()) {
            Aircraft existingAircraft = existingAircraftOpt.get();

            existingAircraft.setType(updatedAircraft.getType());
            existingAircraft.setAirlineName(updatedAircraft.getAirlineName());
            existingAircraft.setNumberOfPassengers(updatedAircraft.getNumberOfPassengers());
            existingAircraft.setAirports(updatedAircraft.getAirports()); // Optional
            existingAircraft.setPassengers(updatedAircraft.getPassengers()); // Optional

            return aircraftRepository.save(existingAircraft);
        }

        return null;
    }
}
