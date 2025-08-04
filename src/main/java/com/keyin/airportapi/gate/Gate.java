package com.keyin.airportapi.gate;

import com.keyin.airportapi.airport.Airport;
import jakarta.persistence.*;

@Entity
public class Gate {
    @Id
    @SequenceGenerator(
            name = "gate_sequence",
            sequenceName = "gate_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gate_sequence"
    )
    private Long gateId;
    private String gateName;
    private String terminal;

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    public Gate() {}

    public Gate(Long gateId, String gateName, String terminal) {
        this.gateId = gateId;
        this.gateName = gateName;
        this.terminal = terminal;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
