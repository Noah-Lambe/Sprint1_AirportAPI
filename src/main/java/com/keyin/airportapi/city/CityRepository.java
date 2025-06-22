package com.keyin.airportapi.city;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    public List<City> findByAirports_Name(String name);
    public List<City> findByPassengers_Phone(String phoneNumber);
}
