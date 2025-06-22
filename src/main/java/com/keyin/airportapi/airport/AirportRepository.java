package com.keyin.airportapi.airport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {
    //List<Airport> findByCity_CityId(int cityId);
    //List<Airport> findByCity_Name(String cityName);
}
