package com.keyin.airportapi.airline;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends CrudRepository<Airline, Long> {
    List<Airline> findByAirlineName(String airlineName);
}
