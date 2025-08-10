package com.keyin.airportapi.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByAirports_AirportName(String airportName);
    List<City> findByPassengers_PhoneNumber(String phoneNumber);
}
