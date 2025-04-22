package com.pigierbackend.securite;


import com.pigierbackend.dto.AuthenticationRequest;
import com.pigierbackend.dto.AuthenticationResponse;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {
    
    @Autowired
     AuthenticationManager authenticationManager;
    
    @Autowired
     UserDetailsService userDetailsService;
    
    @Autowired
     JwtTokenUtil jwtTokenUtil;
    

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponse(token);
    }

    
    // public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) 
    //         throws Exception {
        
    //     try {
    //         authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(
    //                 authenticationRequest.getUsername(), 
    //                 authenticationRequest.getPassword())
    //         );
    //     } catch (BadCredentialsException e) {
    //         throw new Exception("Nom d'utilisateur ou mot de passe incorrect", e);
    //     }
        
    //     final UserDetails userDetails = userDetailsService
    //         .loadUserByUsername(authenticationRequest.getUsername());
        
    //     final String jwt = jwtTokenUtil.generateToken(userDetails);
        
    //     return ResponseEntity.ok(new AuthenticationResponse(jwt));
    // }
}