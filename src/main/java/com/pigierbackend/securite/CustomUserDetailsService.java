package com.pigierbackend.securite;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pigierbackend.utilisateur.*;

import java.util.Collection;
import java.util.Collections;

//import com.pigierbackend.repository.UtilisateurRepository; // Remplacez par le chemin correct de votre repository
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
        return new org.springframework.security.core.userdetails.User(
                utilisateur.getUsername(),
                utilisateur.getPassword(),
              utilisateur.getAuthorities().isEmpty()?Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")):utilisateur.getAuthorities() // Assurez-vous que cette méthode retourne les rôles/autorités
        );
    }
}
