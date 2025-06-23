package com.keyin.airportapi.airport;

import jakarta.persistence.*;
import com.keyin.airportapi.city.*;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    private int airportId;

    private String airportName;
    private String areaCode;
  
    @ManyToOne
    private City city;

    public Airport() {
        // Empty constructor for testing. I read somewhere this is best practice.
    }


    public Airport(int airportId, String airportName, String areaCode, City city) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.areaCode = areaCode;
        this.city = city;
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

    public City getCity() {
        return city;
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

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ID: " + airportId + " Name: " + airportName + " Area Code: " + areaCode + " City: " + city.getName();
    }
}
