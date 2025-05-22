package com.pigierbackend.utilisateur;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

import com.pigierbackend.abstractentity.AbstractEntity;
import com.pigierbackend.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "UTILISATEUR")
@Getter
@Setter
public class Utilisateur extends AbstractEntity /* implements UserDetails */ {
    @Column(unique = true, nullable = false)
    String username;
    String password;
    String nomPrenoms;
    @ManyToAny(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_ROLE", 
    joinColumns = @JoinColumn(name = "utilisateur_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;
  
}
