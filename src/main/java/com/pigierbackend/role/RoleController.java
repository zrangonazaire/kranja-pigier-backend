package com.pigierbackend.role;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestion des rôles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping(value = "/creerRole", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        RoleResponse createdRole = roleService.create(request);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/listDesRoles", produces = "application/json")
    public ResponseEntity<List<RoleResponse>> listDesRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/roleParNom/{nomRole}", produces = "application/json")
    public ResponseEntity<RoleResponse> getRoleByNomRole(@PathVariable String nomRole) {
        return ResponseEntity.ok(roleService.findByNomRole(nomRole));
    }
    @GetMapping(value = "/rolesParUtilisateur/{idUser}", produces = "application/json")
    public ResponseEntity<List<RoleResponse>> getRolesByUser(@PathVariable Long idUser) {
        return ResponseEntity.ok(roleService.findRoleByUser(idUser));
        }
}