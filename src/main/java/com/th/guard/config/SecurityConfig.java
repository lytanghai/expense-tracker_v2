package com.th.guard.config;

import com.th.guard.component.JwtAuthenticationFilter;
import com.th.guard.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


@Autowired
private JwtAuthenticationFilter jwtAuthFilter;

@Autowired
private UserDetailsServiceImpl userDetailsService;

@Autowired
private CorsProps corsProps;


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .csrf().disable()
//    .cors().configurationSource(corsConfigurationSource())
//    .and()
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and()
    .authorizeRequests()
    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    .antMatchers("/api/auth/**", "/h2-console/**").permitAll()
    .anyRequest().authenticated()
    .and()
    .headers().frameOptions().sameOrigin(); // for H2 console


    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}


@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}


@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}


@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
return authConfig.getAuthenticationManager();
}


@Bean
public CorsConfigurationSource corsConfigurationSource() {
CorsConfiguration config = new CorsConfiguration();
config.setAllowedOrigins(List.of(corsProps.getAllowedOrigin()));
config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
config.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With"));
config.setExposedHeaders(List.of("Authorization"));
config.setAllowCredentials(true);
UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
source.registerCorsConfiguration("/**", config);
return source;
}
}