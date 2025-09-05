package com.th.guard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.cors")
public class CorsProps {
private String allowedOrigin = "*";


public String getAllowedOrigin() { return allowedOrigin; }
public void setAllowedOrigin(String allowedOrigin) { this.allowedOrigin = allowedOrigin; }
}