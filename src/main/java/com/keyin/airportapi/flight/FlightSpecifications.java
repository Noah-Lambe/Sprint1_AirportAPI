package com.keyin.airportapi.flight;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class FlightSpecifications {
    public static Specification<Flight> hasOriginAirportId(Long id) {
        return (root, q, cb) -> id == null ? null : cb.equal(root.get("originAirport").get("airportId"), id);
    }
    public static Specification<Flight> hasDestinationAirportId(Long id) {
        return (root, q, cb) -> id == null ? null : cb.equal(root.get("destinationAirport").get("airportId"), id);
    }
    public static Specification<Flight> hasAirlineId(Long id) {
        return (root, q, cb) -> id == null ? null : cb.equal(root.get("airline").get("airlineId"), id);
    }
    public static Specification<Flight> hasGateId(Long id) {
        return (root, q, cb) -> id == null ? null : cb.equal(root.get("gate").get("gateId"), id);
    }
    public static Specification<Flight> hasAircraftId(Long id) {
        return (root, q, cb) -> id == null ? null : cb.equal(root.get("aircraft").get("aircraftId"), id);
    }
    public static Specification<Flight> hasStatus(String s) {
        return (root, q, cb) -> (s == null || s.isBlank()) ? null : cb.equal(root.get("status"), s);
    }
    public static Specification<Flight> flightNumberLike(String fn) {
        return (root, q, cb) -> (fn == null || fn.isBlank()) ? null
                : cb.like(cb.lower(root.get("flightNumber")), "%" + fn.toLowerCase() + "%");
    }
    public static Specification<Flight> departsOnOrAfter(LocalDateTime start) {
        return (root, q, cb) -> start == null ? null : cb.greaterThanOrEqualTo(root.get("departureTime"), start);
    }
    public static Specification<Flight> departsOnOrBefore(LocalDateTime end) {
        return (root, q, cb) -> end == null ? null : cb.lessThanOrEqualTo(root.get("departureTime"), end);
    }
    public static Specification<Flight> arrivesOnOrAfter(LocalDateTime start) {
        return (root, q, cb) -> start == null ? null : cb.greaterThanOrEqualTo(root.get("arrivalTime"), start);
    }
    public static Specification<Flight> arrivesOnOrBefore(LocalDateTime end) {
        return (root, q, cb) -> end == null ? null : cb.lessThanOrEqualTo(root.get("arrivalTime"), end);
    }
}
