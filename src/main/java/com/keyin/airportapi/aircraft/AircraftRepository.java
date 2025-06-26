package com.keyin.airportapi.aircraft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
    @Query("SELECT a FROM Aircraft a JOIN a.passengers p WHERE p.id = :passengerId")
    List<Aircraft> findAircraftByPassengerId(Long passengerId);
}

