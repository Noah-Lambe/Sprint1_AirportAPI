package com.keyin.airportapi.city;

import com.keyin.airportapi.passenger.Passenger;
import com.keyin.airportapi.airport.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return (List<City>) cityRepository.findAll();
    }

    public City getCityById(long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.orElse(null);
    }

    public List<City> getCitiesByAirportName(String name) {
        return cityRepository.findByAirports_AirportName(name);
    }

    public List<City> getCitiesByPassengerPhone(String phoneNumber) {
        return cityRepository.findByPassengers_PhoneNumber(phoneNumber);
    }

    public void deleteCityById(long id) {
        cityRepository.deleteById(id);
    }

    public City updateCity(long id, City city) {
        Optional<City> cityToUpdateOptional = cityRepository.findById(id);

        if (cityToUpdateOptional.isPresent()) {
            City cityToUpdate = cityToUpdateOptional.get();

            cityToUpdate.setName(city.getName());
            cityToUpdate.setState(city.getState());
            cityToUpdate.setPopulation(city.getPopulation());

            if (city.getAirports() != null) {
                for (Airport airport : city.getAirports()) {
                    airport.setCity(cityToUpdate);
                }
                cityToUpdate.setAirports(city.getAirports());
            }

            if (city.getPassengers() != null) {
                for (Passenger passenger : city.getPassengers()) {
                    passenger.setCity(cityToUpdate);
                }
                cityToUpdate.setPassengers(city.getPassengers());
            }

            return cityRepository.save(cityToUpdate);
        }

        return null;
    }

    public City createCity(City city) {
        if (city.getAirports() != null) {
            for (Airport airport : city.getAirports()) {
                airport.setCity(city);
            }
        }

        if (city.getPassengers() != null) {
            for (Passenger passenger : city.getPassengers()) {
                passenger.setCity(city);
            }
        }

        return cityRepository.save(city);
    }

    public List<Airport> getAirportsByCityId(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            return city.getAirports();
        }
        return List.of();
    }
}
