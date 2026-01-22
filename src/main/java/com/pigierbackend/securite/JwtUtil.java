package com.pigierbackend.securite;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.pigierbackend.utilisateur.Utilisateur;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        Utilisateur user = (Utilisateur) userDetails;
        Map<String, Object> claims = new HashMap<>();
     
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("sub", user.getUsername());
        claims.put("nom", user.getName());
        claims.put("nomPrenoms", user.getNomPrenoms());
        claims.put("telephone", user.getTelephone());
        claims.put("roles", user.getRoles().stream().map(role -> {
            Map<String, Object> roleMap = new HashMap<>();
            roleMap.put("id", role.getId());
            roleMap.put("nomRole", role.getNomRole());
            roleMap.put("descriptionRole", role.getDescriptionRole());
            roleMap.put("permissions", role.getPermission().stream().map(permission -> {
                Map<String, Object> permMap = new HashMap<>();
                permMap.put("id", permission.getId());
                permMap.put("nomPermission", permission.getNomPermission());
                permMap.put("descriptionPermission", permission.getDescriptionPermission());
                permMap.put("module", permission.getModule());

                return permMap;

            }).collect(Collectors.toList()));

            return roleMap;
        }));

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        String key = secretKey == null ? "" : secretKey.trim();  // <- IMPORTANT
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}