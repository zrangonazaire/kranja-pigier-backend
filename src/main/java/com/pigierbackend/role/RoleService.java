package com.pigierbackend.role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    Optional<RoleResponse> findById(Long id);
    List<RoleResponse> findAll();
    RoleResponse update(Long id, RoleRequest request);
    void delete(Long id);
}