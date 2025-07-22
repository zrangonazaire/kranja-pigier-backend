package com.pigierbackend.utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    UserResponse save(UserRequest utilisateur);
    UserResponse update(Long id, UserRequest utilisateur);
    void delete(Long id);
    Optional<UserResponse> findById(Long id);
    List<UserResponse> findAll();
}
