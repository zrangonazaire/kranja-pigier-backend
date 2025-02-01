package com.pigierbackend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {
//final JwtRequestFilter jwtRequestFilter;
}
