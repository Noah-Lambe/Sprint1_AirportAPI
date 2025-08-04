package com.keyin.airportapi.flight;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByAirline_Id(Long airlineId);
    List<Flight> findByOriginAirport_Id(Long originAirportId);
    List<Flight> findByDestinationAirport_Id(Long destinationAirportId);
    List<Flight> findByGate_Id(Long gateId);
    List<Flight> findByStatus(String status);
    List<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByAircraft_Id(Long aircraftId);
    List<Flight> findByDepartureTime(LocalDateTime departureTime);
    List<Flight> findByArrivalTime(LocalDateTime arrivalTime);
    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
}

