package com.pigierbackend.securite;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pigierbackend.utilisateur.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        Collection<? extends GrantedAuthority> authorities = utilisateur.getRoles().stream()
                .map(role -> {
                    Set<GrantedAuthority> roleAuthorities = new HashSet<>();
                    roleAuthorities.add(new SimpleGrantedAuthority(role.getNomRole()));
                    role.getPermission().forEach(permission -> roleAuthorities
                            .add(new SimpleGrantedAuthority(permission.getNomPermission())));

                    return roleAuthorities;
                }).flatMap(Set::stream).collect(Collectors.toSet());
        return new User(utilisateur.getUsername(),
                utilisateur.getPassword(),
                authorities);
    }
}
