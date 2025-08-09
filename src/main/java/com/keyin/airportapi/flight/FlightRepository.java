package com.keyin.airportapi.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {

    List<Flight> findByAirline_AirlineId(Long airlineId);
    List<Flight> findByOriginAirport_AirportId(Long originAirportId);
    List<Flight> findByDestinationAirport_AirportId(Long destinationAirportId);
    List<Flight> findByGate_GateId(Long gateId);
    List<Flight> findByStatus(String status);
    List<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByAircraft_AircraftId(Long aircraftId);
    List<Flight> findByDepartureTime(LocalDateTime departureTime);
    List<Flight> findByArrivalTime(LocalDateTime arrivalTime);
    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Flight> findByPassengers_Id(Long passengerId);
}

