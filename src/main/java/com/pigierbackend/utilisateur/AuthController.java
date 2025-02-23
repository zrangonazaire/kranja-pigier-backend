package com.pigierbackend.utilisateur;

import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

  //   AuthenticationManager authenticationManager;
  // //  UtilisateurDetailService utilisateurDetailService;


  //   @PostMapping("authentification")
  //   public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
  //           throws Exception {
  //       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
  //               authenticationRequest.getPassword()));
  //              // final UserDetails userDetails = utilisateurDetailService.loadUserByUsername(authenticationRequest.getUsername());
   
  //  return ResponseEntity.ok(null);
  //           }

}
