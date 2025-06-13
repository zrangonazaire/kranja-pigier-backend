package com.pigierbackend.securite;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

//final CustomUserDetailsService customUserDetailsService;
final JwtAuthEntryPoint jwtAuthEntryPoint;

 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour les API REST
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // Gestion des exceptions d'authentification
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Ajout de la configuration CORS
            .headers(headers -> headers
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:4200")) // Autoriser une origine spécifique
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")) // Méthodes autorisées
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With")) // En-têtes autorisés
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true")) // Autoriser les cookies
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**","/api/v1/auth/**", "/auth/login","/preinscription/**","/encaissement/**","/eleves/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/api/v1/preinscriptionyakro/**").permitAll()
                .requestMatchers("/api/utilisateur/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Ajout du filtre JWT avant le filtre d'authentification par nom d'utilisateur/mot de passe;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//  @Bean
//  public JwtAuthFilter jwtAuthFilter() {
//         return new JwtAuthFilter();
//     }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200"); // Origine autorisée
        configuration.addAllowedOrigin("http://localhost:8084"); // Ajoutez cette ligne si nécessaire
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}