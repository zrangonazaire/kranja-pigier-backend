package com.pigierbackend.utilisateur;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pigierbackend.securite.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.pigierbackend.dto.AuthenticationRequest;
import com.pigierbackend.dto.AuthenticationResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthController {
 final AuthenticationService authenticationService;
  // AuthenticationManager authenticationManager;
  // // UtilisateurDetailService utilisateurDetailService;
@PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest dto) throws Exception {
  
    return ResponseEntity.ok(authenticationService.authenticate(dto));
  }
}

