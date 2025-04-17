package org.example.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) rules.
 * <p>
 * This config allows the frontend (e.g., React on http://localhost:3000)
 * to communicate with the Spring Boot backend.
 */
@Configuration
public class CorsConfig {

    /**
     * Defines global CORS configuration for the application.
     *
     * @return WebMvcConfigurer instance with configured CORS mappings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Applies to all endpoints
                        .allowedOrigins("http://localhost:3000") // Your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // If using cookies/session
            }
        };
    }
}
