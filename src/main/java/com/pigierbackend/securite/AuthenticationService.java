package com.pigierbackend.securite;

import com.pigierbackend.dto.AuthenticationRequest;
import com.pigierbackend.dto.AuthenticationResponse;
import com.pigierbackend.utilisateur.UtilisateurRepository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    JwtUtil jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Utilisateur non trouvé"));
           // log.info("User is desactive: {}", user.isEnabled());

            // Vérification si le compte est désactivé
            // if (!user.isEnabled()) {
            // throw new DisabledException(
            // "Votre compte est désactivé. Veuillez contacter l'administrateur.");
            // }
            // Vérifications avant authentification
            // if (isPasswordExpired(user.getUsername())) {
            // throw new RuntimeException(
            // "Votre mot de passe a expiré. Veuillez le réinitialiser.");
            // }

            // if (isAccountInactive(user.getEmail())) {
            // throw new DisabledException(
            // "Votre compte est inactif. Veuillez demander une réactivation.");
            // }

            // Mise à jour de la dernière connexion
            // user.setLastLoginDate(LocalDateTime.now());
            // userRepository.save(user);

            // Génération du token JWT
            var claims = new HashMap<String, Object>();
            claims.put("fullUser", user.toUserResponse());
            var jwtToken = jwtService.generateToken(claims, user);

            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email ou mot de passe incorrect", e);
        } catch (DisabledException e) {
            throw new RuntimeException("Compte désactivé", e);
        } catch (LockedException e) {
            throw new RuntimeException("Compte verrouillé", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur d'authentification", e);
        }
    }
}