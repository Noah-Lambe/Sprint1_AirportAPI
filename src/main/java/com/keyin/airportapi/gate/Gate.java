package com.keyin.airportapi.gate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.keyin.airportapi.airport.Airport;
import jakarta.persistence.*;

@Entity
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long gateId;
    private String gateName;
    private String terminal;

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    @JsonIgnoreProperties({"gates"})
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
