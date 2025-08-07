package com.keyin.airportapi.passenger;

public class PassengerRequest {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long cityId;

    public PassengerRequest() {}

    /** new: 4-arg ctor for creating brand-new passengers */
    public PassengerRequest(String firstName,
                            String lastName,
                            String phoneNumber,
                            Long cityId) {
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.phoneNumber = phoneNumber;
        this.cityId      = cityId;
    }

    /** you already had this, but make sure it’s here: */
    public PassengerRequest(Long passengerId,
                            String firstName,
                            String lastName,
                            String phoneNumber,
                            Long cityId) {
        this.passengerId = passengerId;
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.phoneNumber = phoneNumber;
        this.cityId      = cityId;
    }

    // getters & setters

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
