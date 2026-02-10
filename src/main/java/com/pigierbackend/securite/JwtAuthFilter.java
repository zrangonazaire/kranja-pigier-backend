package com.pigierbackend.securite;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pigierbackend.utilisateur.Utilisateur;
import com.pigierbackend.utilisateur.UtilisateurRepository;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        log.info("JWT Filter - URI: {}, Method: {}", request.getRequestURI(), request.getMethod());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("JWT Filter - No Bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        log.info("JWT Filter - Token found, validating...");

        try {
            String username = jwtService.extractUsername(jwt);
            log.info("JWT Filter - Extracted username: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                Utilisateur userDetails = utilisateurRepository.findByUsername(username)
                        .orElse(null);

                if (userDetails == null) {
                    log.error("JWT Filter - User not found: {}", username);
                    filterChain.doFilter(request, response);
                    return;
                }

                log.info("JWT Filter - User found: {}", userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.info("JWT Filter - Token is VALID for user: {}", username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("JWT Filter - Authentication set in SecurityContext for: {}", username);
                } else {
                    log.error("JWT Filter - Token is INVALID for user: {}", username);
                }
            }

        } catch (Exception e) {
            log.error("JWT AUTH ERROR: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
