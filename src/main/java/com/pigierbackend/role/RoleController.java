package com.pigierbackend.role;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestion des rôles")
public class RoleController {

    private final RoleService roleService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/creerRole", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        RoleResponse createdRole = roleService.create(request);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/listDesRoles", produces = "application/json")
    public ResponseEntity<List<RoleResponse>> listDesRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/roleParNom/{nomRole}", produces = "application/json")
    public ResponseEntity<RoleResponse> getRoleByNomRole(@PathVariable String nomRole) {
        return ResponseEntity.ok(roleService.findByNomRole(nomRole));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/rolesParUtilisateur/{idUser}", produces = "application/json")
    public ResponseEntity<List<RoleResponse>> getRolesByUser(@PathVariable Long idUser) {
        return ResponseEntity.ok(roleService.findRoleByUser(idUser));
        }
}