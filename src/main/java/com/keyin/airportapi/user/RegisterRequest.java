package com.keyin.airportapi.user;

public class RegisterRequest {
    private String username;
    private String password;
    private String role;

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Long cityId;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFirstName() { return firstname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }

    public String getLastName() { return lastname; }
    public void setLastName(String lastname) { this.lastname = lastname; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
}