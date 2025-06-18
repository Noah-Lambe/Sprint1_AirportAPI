package com.keyin.airportapi.passenger;

import com.keyin.airportapi.city.City;
import jakarta.persistence.*;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City city;

    @Column(nullable = false)
    private String phone;

    public void setCity(City city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
