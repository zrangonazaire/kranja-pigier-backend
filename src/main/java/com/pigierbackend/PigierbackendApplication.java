package com.pigierbackend;

import java.util.Optional;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.permission.PermissionRepository;
import com.pigierbackend.role.Role;
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

    // @Bean
    // public CorsFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration corsConfiguration = new CorsConfiguration();
    //     corsConfiguration.setAllowCredentials(true);
    //     corsConfiguration.setAllowedOriginPatterns(
    //             Arrays.asList(
    //                     "http://localhost:4200",
    //                     "*"));
    //     corsConfiguration.setAllowedHeaders(
    //             Arrays.asList(
    //                     "Origin",
    //                     "Access-Control-Allow-Origin",
    //                     "Content-Type",
    //                     "Accept",
    //                     "Authorization",
    //                     "Origin, Accept",
    //                     "X-Requested-With",
    //                     "Access-Control-Request-Method",
    //                     "Access-Control-Request-Headers",
    //                     "Jwt-Token",
    //                     "Content-Disposition"));
    //     corsConfiguration.setExposedHeaders(
    //             Arrays.asList(
    //                     "Origin",
    //                     "Content-Type",
    //                     "Accept",
    //                     "Authorization",
    //                     "Access-Control-Allow-Origin",
    //                     "Access-Control-Allow-Credentials",
    //                     "Content-Disposition"));
    //     corsConfiguration.setAllowedMethods(
    //             Arrays.asList(
    //                     "GET",
    //                     "POST",
    //                     "PUT",
    //                     "DELETE",
    //                     "OPTIONS"));
    //     urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    //     return new CorsFilter(urlBasedCorsConfigurationSource);
    // }

    @Bean
    public CommandLineRunner chargerDonnees(
            PermissionRepository privilegeRepository, RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            String mdp = passwordEncoder.encode("Pigierci");
            int nombreprivilege = privilegeRepository.findAll().size();
            if (nombreprivilege == 0) {
                privilegeRepository.save(new Permission("PRIVILEGE_ADMIN", "Administrateur"));
                privilegeRepository.save(new Permission("PRIVILEGE_USER", "Utilisateur"));
                privilegeRepository.save(new Permission("PRIVILEGE_GESTIONNAIRE", "Gestionnaire"));
                privilegeRepository.save(new Permission("PRIVILEGE_PROFESSEUR", "Professeur"));
                privilegeRepository.save(new Permission("PRIVILEGE_ETUDIANT", "Etudiant"));
            }
            int nombreRole = roleRepository.findAll().size();
            if (nombreRole == 0) {
                roleRepository
                        .save(new Role("ROLE_SUPER_ADMIN", "Super Administrateur", privilegeRepository.findAll()));
            }
            Utilisateur utilisateur = utilisateurRepository.findByUsername("superadmin").orElse(null);
            Optional<Role> role = roleRepository.findRoleByNomRole("ROLE_SUPER_ADMIN");
            if (utilisateur == null) {
                utilisateur = new Utilisateur();
                utilisateur.setUsername("superadmin");
                utilisateur.setPassword(mdp);
                utilisateur.setNomPrenoms("ZRANGO GONAQUET ASTAIRE NAZAIRE");
                utilisateur.setRoles(role.stream().toList());
                utilisateurRepository.save(utilisateur);
            }

        };
    }
}