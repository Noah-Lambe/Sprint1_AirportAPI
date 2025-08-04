package com.keyin.airportapi.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/gates")
public class GateController {
    @Autowired
    private GateService gateService;

    @GetMapping
    public ResponseEntity<List<Gate>> getAllGates() {
        try {
            List<Gate> gates = gateService.getAllGates();
            return ResponseEntity.ok(gates);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gate> getGateById(@PathVariable Long id) {
        try {
            Gate gate = gateService.getGateById(id);
            return ResponseEntity.ok(gateService.getGateById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/airport/{airportId}")
    public ResponseEntity<List<Gate>> getGatesByAirportId(@PathVariable Long airportId) {
        try {
            List<Gate> gates = gateService.getGatesByAirportId(airportId);
            return ResponseEntity.ok(gates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/terminal/{terminal}")
    public ResponseEntity<List<Gate>> getGatesByTerminal(@PathVariable String terminal) {
        try {
            List<Gate> gates = gateService.getGatesByTerminal(terminal);
            return ResponseEntity.ok(gates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{gateName}")
    public ResponseEntity<List<Gate>> getGatesByGateName(@PathVariable String gateName) {
        try {
            List<Gate> gates = gateService.getGatesByGateName(gateName);
            return ResponseEntity.ok(gates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Gate> createGate(@RequestBody Gate gate) {
        try {
            Gate createdGate = gateService.createGate(gate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gate> updateGate(@PathVariable Long id, @RequestBody Gate gateData) {
        try {
            Gate updatedGate = gateService.updateGate(id, gateData);
            return ResponseEntity.ok(updatedGate);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGate(@PathVariable Long id) {
        try {
            gateService.deleteGate(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
