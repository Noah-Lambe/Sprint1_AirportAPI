package com.keyin.airportapi.aircraft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    @Query("SELECT a FROM Aircraft a JOIN a.passengers p WHERE p.id = :passengerId")
    List<Aircraft> findAircraftByPassengerId(@Param("passengerId") Long passengerId);

    @Query("SELECT a FROM Aircraft a LEFT JOIN FETCH a.airports WHERE a.aircraftId = :id")
    Optional<Aircraft> findByIdWithAirports(@Param("id") Long id);

    List<Aircraft> findByAirlineName(String airlineName);
}
