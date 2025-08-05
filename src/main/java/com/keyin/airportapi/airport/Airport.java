package com.keyin.airportapi.airport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.airportapi.gate.Gate;
import jakarta.persistence.*;
import com.keyin.airportapi.city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    private String airportName;
    private String areaCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("airports")
    private City city;

    @OneToMany(mappedBy = "airport")
    private List<Gate> gates;

    public Airport() {
        // Empty constructor for testing. I read somewhere this is best practice.
    }

    public Airport(Long airportId, String airportName, String areaCode, City city) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.areaCode = areaCode;
        this.city = city;
    }

    // Getters
    public Long getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public City getCity() {
        return city;
    }

    public List<Gate> getGates() {
        return gates;
    }

    // Setters
    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    @Override
    public String toString() {
        return "ID: " + airportId + " Name: " + airportName + " Area Code: " + areaCode + " City: " + city.getName();
    }
}
