package com.pigierbackend.role;

import com.pigierbackend.permission.Permission;
import com.pigierbackend.permission.PermissionRepository;
import com.pigierbackend.utilisateur.UtilisateurRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public RoleResponse create(RoleRequest request) {

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionIds()));
        log.info("permissions: {}", permissions);
        URole role = new URole(request.getNomRole(), request.getDescriptionRole(), permissions);
        return roleRepository.save(role).toRoleResponse();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleResponse> findById(Long id) {
        return roleRepository.findById(id).map(URole::toRoleResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream().map(URole::toRoleResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, RoleRequest request) {
        URole role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable"));

        role.setNomRole(request.getNomRole());
        role.setDescriptionRole(request.getDescriptionRole());

        Set<Permission> permissions =
                permissionRepository.findAllById(request.getPermissionIds())
                        .stream().collect(Collectors.toSet());

        role.setPermission(permissions);

        return roleRepository.save(role).toRoleResponse();
    }


    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponse findByNomRole(String nomRole) {
       return roleRepository.findFirstByNomRoleOrderByIdAsc(nomRole)
               .map(URole::toRoleResponse)
               .orElseThrow(() -> new RuntimeException("Rôle non trouvé avec le nom " + nomRole));
    }

    @Override
    public List<RoleResponse> findRoleByUser(Long idUser) {
        return utilisateurRepository.findById(idUser)
                .map(user -> user.getRoles().stream()
                        .map(URole::toRoleResponse)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id " + idUser));
    }
}
