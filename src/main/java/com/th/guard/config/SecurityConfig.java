//package com.th.guard.config;
//
//import com.th.guard.component.JwtAuthFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtAuthFilter jwtFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .cors().configurationSource(corsConfigurationSource())
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // ✅ Automatically allow preflight OPTIONS for public endpoints
//                .antMatchers(HttpMethod.OPTIONS, "/api/auth/login",
//                        "/api/auth/register",
//                        "/api/auth/change-password").permitAll()
//                // ✅ Public endpoints
//                .antMatchers(
//                        "/api/auth/login",
//                        "/api/auth/register",
//                        "/api/auth/change-password"
//                ).permitAll()
//                // ✅ All other requests require authentication
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JwtAuthFilter() {
//                    @Override
//                    protected void doFilterInternal(HttpServletRequest request,
//                                                    HttpServletResponse response,
//                                                    FilterChain filterChain)
//                            throws ServletException, IOException {
//                        // ✅ Skip JWT validation for OPTIONS and public endpoints
//                        if (request.getMethod().equalsIgnoreCase("OPTIONS") ||
//                                request.getRequestURI().startsWith("/api/auth/login") ||
//                                request.getRequestURI().startsWith("/api/auth/register") ||
//                                request.getRequestURI().startsWith("/api/auth/change-password")) {
//                            filterChain.doFilter(request, response);
//                            return;
//                        }
//                        // Otherwise proceed with normal JWT filter
//                        super.doFilterInternal(request, response, filterChain);
//                    }
//                }, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        // ✅ Global free CORS for any frontend
//        config.addAllowedOriginPattern("*"); // allow any origin dynamically
//        config.addAllowedMethod("*");        // allow all HTTP methods
//        config.addAllowedHeader("*");        // allow all headers
//        config.setAllowCredentials(false);   // must be false if using "*"
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//}
