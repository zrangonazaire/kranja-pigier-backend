package com.pigierbackend.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import static org.springframework.security.config.Customizer.withDefaults;

import com.pigierbackend.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

//     private final AuthenticationProvider authenticationProvider;
//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
//         this.authenticationProvider = authenticationProvider;
//         this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http
//         .cors(withDefaults())
//                 .csrf(AbstractHttpConfigurer::disable) // Désactive CSRF
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(
//                                 "/api/v1/v3/api-docs",
//                                 "/api/v1/v3/api-docs/**",
//                                 "/api/v1/swagger-ui/**",
//                                 "/api/v1/swagger-ui.html",
//                                 "/api/v1/swagger-resources/**",
//                                 "/api/v1/webjars/**",
//                                 "/api/v1/auth/**",
//                                 "/api/v1/etablissement/**",
//                                 "/v2/api-docs",
//                     "/v3/api-docs",
//                     "/v3/api-docs/**",
//                     "/swagger-resources",
//                     "/swagger-resources/**",
//                     "/configuration/ui",
//                     "/configuration/security",
//                     "/swagger-ui/**",
//                     "/swagger-ui.html",
//                     "/webjars/**",
//                     "/api/v1/auth/**",
//                     "/api/v1/etablissement/**",
//                     "/api/v1/swagger-ui/**",
//                     "/api/v1/swagger-ui.html",
//                     "/api/v1/v3/api-docs/**" // Autorise les ressources statiques
//                         ).permitAll() // Autorise l'accès sans authentification
//                         .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
//                 )
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Gestion de session sans état
//                 )
//                 .authenticationProvider(authenticationProvider) // Configure le fournisseur d'authentification
//                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre JWT
//                 .build();
//     }
}