package com.keyin.airportapi.gate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends CrudRepository<Gate, Long> {
    List<Gate> findByAirport_Id(Long airportId);
    List<Gate> findByTerminal(String terminal);
    List<Gate> findByGateName(String gateName);
}
