package com.th.guard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return new Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
    // Optional: Provide ObjectMapper if needed directly
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://expense-tracker-v2-web.onrender.com")
                        .allowedMethods("GET", "POST", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}