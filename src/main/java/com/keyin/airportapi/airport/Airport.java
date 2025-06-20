package com.keyin.airportapi.airport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    private int airportId;

    private String airportName;
    private String areaCode;
    // private City city;

    public Airport() {
        // Empty constructor for testing. I read somewhere this is best practice.
    }

    public Airport(int airportId, String airportName, String areaCode) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.areaCode = areaCode;
    }

    // Getters
    public int getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    // Setters
    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return airportId + " " + airportName + " " + areaCode;
    }
}