package com.keyin.airportapi.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GateService {
    @Autowired
    private GateRepository gateRepository;

    public List<Gate> getAllGates() {
        return (List<Gate>)gateRepository.findAll();
    }

    public Gate getGateById(Long id) {
        return gateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gate not found"));
    }

    public List<Gate> getGatesByAirportId(Long airportId) {
        return gateRepository.findByAirport_Id(airportId);
    }

    public List<Gate> getGatesByTerminal(String terminal) {
        return gateRepository.findByTerminal(terminal);
    }

    public List<Gate> getGatesByGateName(String gateName) {
        return gateRepository.findByGateName(gateName);
    }

    public Gate createGate(Gate gate) {
        return gateRepository.save(gate);
    }

    public Gate updateGate(Long id, Gate gateData) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gate not found"));

        gate.setTerminal(gateData.getTerminal());
        gate.setGateName(gateData.getGateName());
        gate.setAirport(gateData.getAirport());

        return gateRepository.save(gate);
    }

    public void deleteGate(Long id) {
        gateRepository.deleteById(id);
    }
}
