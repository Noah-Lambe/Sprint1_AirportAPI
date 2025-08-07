package com.keyin.airportapi.user;

public class RegisterResponse {
    private Long userId;
    private String username;
    private Long passengerId;

    public RegisterResponse(Long userId, String username, Long passengerId) {
        this.userId      = userId;
        this.username    = username;
        this.passengerId = passengerId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getPassengerId() {
        return passengerId;
    }
}
