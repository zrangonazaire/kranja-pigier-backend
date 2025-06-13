package com.pigierbackend;

import java.util.Optional;
import java.util.Set;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.permission.PermissionRepository;
import com.pigierbackend.role.URole;
import com.pigierbackend.role.RoleRepository;
import com.pigierbackend.utilisateur.Utilisateur;
import com.pigierbackend.utilisateur.UtilisateurRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@RequiredArgsConstructor
@EnableScheduling
public class PigierbackendApplication extends SpringBootServletInitializer { // Étendre SpringBootServletInitializer

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PigierbackendApplication.class); // Indiquer la classe principale
    }

    public static void main(String[] args) {
        SpringApplication.run(PigierbackendApplication.class, args);
    }


    @Bean
    public CommandLineRunner chargerDonnees(
            PermissionRepository permissionRepository, RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Creation des permissions
            Permission lirePreinscription = permissionRepository.findByNomPermission("LIRE_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(new Permission("LIRE_PREINSCRIPTION", "Lire les préinscriptions")));

            Permission ecrirePreinscription = permissionRepository.findByNomPermission("ECRIRE_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(new Permission("ECRIRE_PREINSCRIPTION", "Créer les préinscriptions")));

            Permission modifPreinscription = permissionRepository.findByNomPermission("MODIF_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(new Permission("MODIF_PREINSCRIPTION", "Modifier les préinscriptions")));
            URole roleAdmin = roleRepository.findByNomRole("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new URole("ROLE_ADMIN", "Administrateur",
                            Set.of(lirePreinscription, ecrirePreinscription, modifPreinscription))));
            URole roleCommerciale = roleRepository.findByNomRole("ROLE_COMMERCIALE").orElseGet(() -> {
                URole role = new URole("ROLE_COMMERCIALE", "Commerciale",
                        Set.of(lirePreinscription, ecrirePreinscription, modifPreinscription));
                return roleRepository.save(role);
            });

            // CREATION DES UTILISATEURS
            // UTILISATEUR ADMIN
            String mdp = passwordEncoder.encode("Pigierci@2025");
            if (utilisateurRepository.findByUsername("admin").isEmpty()) {
                Utilisateur utilisateurAdmin = new Utilisateur();
                utilisateurAdmin.setUsername("admin");
                utilisateurAdmin.setPassword(mdp);
                utilisateurAdmin.setNomPrenoms("ZRANGO GONAQUET ASTAIRE NAZAIRE");
                utilisateurAdmin.getRoles().add(roleAdmin);
                utilisateurRepository.save(utilisateurAdmin);

            }
            // UTILISATEUR COMMERCIALE
            if (utilisateurRepository.findByUsername("commerciale").isEmpty()) {
                Utilisateur utilisateurCommerciale = new Utilisateur();
                utilisateurCommerciale.setUsername("commerciale");
                utilisateurCommerciale.setPassword(mdp);
                utilisateurCommerciale.setNomPrenoms("TEST COMMERCIALE");
                utilisateurCommerciale.getRoles().add(roleCommerciale);
                utilisateurRepository.save(utilisateurCommerciale);
            }

        };
    }
}