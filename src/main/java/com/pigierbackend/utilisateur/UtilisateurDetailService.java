package com.pigierbackend.utilisateur;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilisateurDetailService implements UserDetailsService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        Collection<GrantedAuthority> authorities = utilisateur.getRoles().stream()
                .map(role -> (GrantedAuthority) role) // Cast each Role to GrantedAuthority
                .collect(Collectors.toList());
        return new User(utilisateur.getUsername(), utilisateur.getPassword(), authorities);
    }
}
