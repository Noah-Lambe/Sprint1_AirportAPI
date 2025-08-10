package com.keyin.airportapi.gate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
    List<Gate> findByAirport_AirportId(Long airportId);
    List<Gate> findByTerminal(String terminal);
    List<Gate> findByGateName(String gateName);
}
