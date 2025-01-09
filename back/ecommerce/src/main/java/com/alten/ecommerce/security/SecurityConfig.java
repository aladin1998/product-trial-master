package com.alten.ecommerce.security;

import com.alten.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.headers(header -> header.frameOptions(frameOptions -> frameOptions.disable()));

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**" ).permitAll());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/products/**").authenticated());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/cart/**").authenticated());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/wishlist/**").authenticated());


        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
