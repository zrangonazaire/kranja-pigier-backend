package com.pigierbackend.permission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        log.info("Création d'une nouvelle permission nom  : {}, description : {}, module : {}", request.getNomPermission(), request.getDescriptionPermission(), request.getModule());
        Permission permission = request.toPermission();

        return permissionRepository.save(permission).toPermissionResponse();
    }

    @Override
    public Optional<PermissionResponse> findById(Long id) {
        return permissionRepository.findById(id).map(Permission::toPermissionResponse);
    }

    @Override
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll()
                .stream()
                .map(Permission::toPermissionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission non trouvée avec l'id " + id));
        permission.setNomPermission(request.getNomPermission());
        permission.setDescriptionPermission(request.getDescriptionPermission());
        return permissionRepository.save(permission).toPermissionResponse();
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}