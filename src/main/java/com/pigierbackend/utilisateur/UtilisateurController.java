package com.pigierbackend.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest utilisateur) {
        utilisateur.setStatut(StatutUtilisateur.ACTIVE);
        return ResponseEntity.ok(utilisateurService.save(utilisateur));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = "application/json")
    public List<UserResponse> getAll() {
        return utilisateurService.findAll();
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest utilisateur) {
        return ResponseEntity.ok(utilisateurService.update(id, utilisateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/username/{username}", produces = "application/json")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(utilisateurService.findByUsername(username));
    }
}
