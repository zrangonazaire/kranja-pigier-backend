package com.pigierbackend.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // final CustomUserDetailsService customUserDetailsService;
    final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour les API REST
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // Gestion des
                                                                                                       // exceptions
                                                                                                       // d'authentification
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Ajout de la configuration CORS
                .headers(headers -> headers
                        .cacheControl(withDefaults())
                        .contentTypeOptions(withDefaults())
                        .httpStrictTransportSecurity(withDefaults()) // Désactiver le cache
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/v1/auth/**", "/auth/login", "/preinscription/**",
                                "/api/v1/preinscription/**", "/encaissement/**", "/eleves/**", "/utilisateurs/**",
                                "/permissions/**", "/roles/**","/annees-academiques/**","/recapinscri/**")
                        .permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/v1/preinscriptionyakro/**")
                        .permitAll()
                        .requestMatchers("/api/utilisateur/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Ajout du filtre JWT
                                                                                             // avant le filtre
                                                                                             // d'authentification par
                                                                                             // nom d'utilisateur/mot de
                                                                                             // passe;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public JwtAuthFilter jwtAuthFilter() {
    // return new JwtAuthFilter();
    // }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

 @Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(
        "http://localhost:4200",
        "http://192.168.0.134:8084",
        "http://192.168.0.125:8000",
        "http://192.168.0.125:8084",
        "http://localhost:8084"
    ));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
}