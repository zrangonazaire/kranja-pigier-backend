package com.pigierbackend.permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    Optional<PermissionResponse> findById(Long id);
    List<PermissionResponse> findAll();
    PermissionResponse update(Long id, PermissionRequest request);
    void delete(Long id);
}