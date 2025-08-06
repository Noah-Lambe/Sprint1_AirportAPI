package com.keyin.airportapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                               // all paths
                .allowedOrigins("http://localhost:5173")         // your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // HTTP verbs you need
                .allowedHeaders("*")
                .allowCredentials(true);                        // if you send cookies or auth headers
    }
}

