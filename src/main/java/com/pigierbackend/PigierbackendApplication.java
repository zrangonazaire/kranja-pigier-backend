package com.pigierbackend;

import java.util.Set;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.permission.PermissionRepository;
import com.pigierbackend.role.URole;
import com.pigierbackend.role.RoleRepository;
import com.pigierbackend.utilisateur.Utilisateur;
import com.pigierbackend.utilisateur.UtilisateurRepository;

import jakarta.persistence.EntityListeners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.ObjectInputFilter; // Ajouté: filtre global pour la désérialisation (mitigation CVE)

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@RequiredArgsConstructor
@EnableScheduling
@EntityListeners(AuditingEntityListener.class)
public class PigierbackendApplication extends SpringBootServletInitializer { // Étendre SpringBootServletInitializer

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PigierbackendApplication.class); // Indiquer la classe principale
    }

    public static void main(String[] args) {
        // Mitigation: apply a conservative deserialization filter to block unsafe classes
        // This reduces risk from libraries (ex: JasperReports) that may perform deserialization
        // See: CVE-2025-10492 (JasperReports) — apply filtering until an upstream patch is available
        var baseFilter = ObjectInputFilter.Config.createFilter("java.base/*;com.pigierbackend.*;org.springframework.*;!*");
        var rejectDangerous = ObjectInputFilter.rejectFilter(cl ->
                cl == java.lang.Runtime.class || cl == java.lang.ProcessBuilder.class || cl == java.lang.Class.class,
                ObjectInputFilter.Status.REJECTED);
        var combined = ObjectInputFilter.merge(rejectDangerous, baseFilter);
        ObjectInputFilter.Config.setSerialFilter(ObjectInputFilter.rejectUndecidedClass(combined));

        SpringApplication.run(PigierbackendApplication.class, args);
    }

    @Bean
    CommandLineRunner chargerDonnees(
            PermissionRepository permissionRepository, RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Creation des permissions
            Permission lirePreinscription = permissionRepository.findByNomPermission("LIRE_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(Permission.builder()
                                    .nomPermission("LIRE_PREINSCRIPTION")
                                    .descriptionPermission("Lire les préinscriptions")
                                    .module("Preinscription")
                                    .canRead(true)
                                    .canWrite(false)
                                    .canEdit(false)
                                    .canDelete(false)
                                    .build()));

            Permission ecrirePreinscription = permissionRepository.findByNomPermission("ECRIRE_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(Permission.builder()
                                    .nomPermission("ECRIRE_PREINSCRIPTION")
                                    .descriptionPermission("Créer les préinscriptions")
                                    .module("PREINSCRIPTION")
                                    .canRead(true)
                                    .canWrite(true)
                                    .canEdit(true)
                                    .canDelete(false)
                                    .build()));

            Permission modifPreinscription = permissionRepository.findByNomPermission("MODIF_PREINSCRIPTION")
                    .orElseGet(() -> permissionRepository
                            .save(Permission.builder()
                                    .nomPermission("MODIF_PREINSCRIPTION")
                                    .descriptionPermission("Modifier les préinscriptions")
                                    .module("PREINSCRIPTION")
                                    .canRead(true)
                                    .canWrite(false)
                                    .canEdit(true)
                                    .canDelete(false)
                                    .build()));
            URole roleAdmin = roleRepository.findByNomRole("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new URole("ROLE_ADMIN", "Administrateur",
                            Set.of(lirePreinscription, ecrirePreinscription, modifPreinscription))));
            URole roleCommerciale = roleRepository.findByNomRole("ROLE_COMMERCIALE").orElseGet(() -> {
                URole role = new URole("ROLE_COMMERCIALE", "Commerciale",
                        Set.of(lirePreinscription, ecrirePreinscription, modifPreinscription));
                return roleRepository.save(role);
            }
            );

            // CREATION DES UTILISATEURS
            // UTILISATEUR ADMIN
            String mdp = passwordEncoder.encode("Pigierci@2025");
            if (utilisateurRepository.findByUsername("admin").isEmpty()) {
                Utilisateur utilisateurAdmin = Utilisateur.builder()
                        .username("admin")
                        .password(mdp)
                        .nomPrenoms("ZRANGO GONAQUET ASTAIRE NAZAIRE")
                        .roles(Set.of(roleAdmin))
                        .build();
                utilisateurRepository.save(utilisateurAdmin);

            }
            // UTILISATEUR COMMERCIALE
            if (utilisateurRepository.findByUsername("commerciale").isEmpty()) {
                Utilisateur utilisateurCommerciale = Utilisateur.builder()
                        .username("commerciale")
                        .password(mdp)
                        .nomPrenoms("TEST COMMERCIALE")
                        .roles(Set.of(roleCommerciale))
                        .build();
                utilisateurRepository.save(utilisateurCommerciale);
            }

        };
    }
}