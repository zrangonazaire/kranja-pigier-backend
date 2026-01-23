package com.pigierbackend.securite;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.role.URole;
import com.pigierbackend.utilisateur.*;

import java.util.*;
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

        boolean accountEnabled = utilisateur.getStatut() == StatutUtilisateur.ACTIVE;

        return new User(
                utilisateur.getUsername(),
                utilisateur.getPassword(),
                accountEnabled,
                true,
                true,
                true,
                getAuthorities(utilisateur.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<URole> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (URole role : roles) {
            // IMPORTANT : Ajouter le rôle avec le préfixe ROLE_
            if (!role.getNomRole().startsWith("ROLE_")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNomRole()));
            } else {
                authorities.add(new SimpleGrantedAuthority(role.getNomRole()));
            }

            // Ajouter les permissions
            for (Permission permission : role.getPermission()) {
                String module = permission.getModule().toUpperCase().replace(" ", "_");

                if (permission.isCanRead())
                    authorities.add(new SimpleGrantedAuthority("READ_" + module));
                if (permission.isCanWrite())
                    authorities.add(new SimpleGrantedAuthority("WRITE_" + module));
                if (permission.isCanEdit())
                    authorities.add(new SimpleGrantedAuthority("EDIT_" + module));
                if (permission.isCanDelete())
                    authorities.add(new SimpleGrantedAuthority("DELETE_" + module));
            }
        }

        return authorities;
    }

    private List<SimpleGrantedAuthority> getPermissionsAsAuthorities(Permission permission) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String module = permission.getModule().toUpperCase().replace(" ", "_");
        
        if (permission.isCanRead()) {
            authorities.add(new SimpleGrantedAuthority("READ_" + module));
        }
        if (permission.isCanWrite()) {
            authorities.add(new SimpleGrantedAuthority("WRITE_" + module));
        }
        if (permission.isCanEdit()) {
            authorities.add(new SimpleGrantedAuthority("EDIT_" + module));
        }
        if (permission.isCanDelete()) {
            authorities.add(new SimpleGrantedAuthority("DELETE_" + module));
        }
        
        return authorities;
    }
}
