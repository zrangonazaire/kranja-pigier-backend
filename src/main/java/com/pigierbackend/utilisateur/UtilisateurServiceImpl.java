package com.pigierbackend.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.role.RoleRepository;
import com.pigierbackend.role.URole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(UserRequest userRequest) {
        // 1. Créer l'entité Utilisateur à partir du DTO
        Utilisateur newUser = userRequest.toUtilisateur();
        newUser.setId(userRequest.getId());
        if (newUser.getStatut() == null) {
            newUser.setStatut(StatutUtilisateur.ACTIVE);
        }

        // 2. Récupérer les entités URole et les associer
        if (userRequest.getRoleIds() != null && !userRequest.getRoleIds().isEmpty()) {
            Set<URole> roles = new HashSet<>(roleRepository.findAllById(userRequest.getRoleIds()));
            if (roles.size() != userRequest.getRoleIds().size()) {
                log.warn("Certains IDs de rôle n'ont pas été trouvés et seront ignorés.");
                // Optionnellement, lancer une exception pour être plus strict
            }
            newUser.setRoles(roles);
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // 3. Sauvegarder l'entité complète
        Utilisateur savedUser = utilisateurRepository.save(newUser);

        // 4. Retourner la réponse
        return savedUser.toUserResponse();
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        Utilisateur existingUser = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id " + id));

        // Mettre à jour les champs
        existingUser.setUsername(userRequest.getUsername());
        existingUser.setNomPrenoms(userRequest.getNomPrenoms());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setTelephone(userRequest.getTelephone());
        existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if (userRequest.getStatut() != null) {
            if (userRequest.getStatut() == StatutUtilisateur.DESACTIVE) {
                throw new RuntimeException("Le statut DESACTIVE n'est pas autorise via la modification.");
            }
            existingUser.setStatut(userRequest.getStatut());
        }

        // Mettre à jour les rôles
        if (userRequest.getRoleIds() != null) {
            Set<URole> roles = new HashSet<>(roleRepository.findAllById(userRequest.getRoleIds()));
            existingUser.setRoles(roles);
        }

        return utilisateurRepository.save(existingUser).toUserResponse();
    }

    @Override
    public void delete(Long id) {
        Utilisateur existingUser = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouv\u00e9 avec l'id " + id));
        existingUser.setStatut(StatutUtilisateur.DESACTIVE);
        utilisateurRepository.save(existingUser);
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        return utilisateurRepository.findById(id).map(Utilisateur::toUserResponse);
    }

    @Override
    public List<UserResponse> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(Utilisateur::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse findByUsername(String username) {
        return utilisateurRepository.findByUsername(username)
                .map(Utilisateur::toUserResponse)
                .orElseThrow(
                        () -> new RuntimeException("Utilisateur non trouvé avec le nom d'utilisateur " + username));
    }
}
