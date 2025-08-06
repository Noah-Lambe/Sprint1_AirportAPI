package com.keyin.airportapi;

import com.keyin.airportapi.user.RegisterRequest;
import com.keyin.airportapi.user.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AirportapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AirportapiApplication.class, args);
	}
}

