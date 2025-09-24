package com.pigierbackend.utilisateur;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.pigierbackend.abstractentity.AbstractEntity;
import com.pigierbackend.role.URole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "UTILISATEUR")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends AbstractEntity implements UserDetails, Principal /* implements UserDetails */ {
    @Column(unique = true, nullable = false)
    String username;
    String password;
    String nomPrenoms;
    String telephone;
    String email;
    Boolean enable;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_ROLE", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<URole> roles = new HashSet<>();

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNomRole()))
                .toList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserResponse toUserResponse() {
        return UserResponse.builder()
                .id(getId())
                .username(getUsername())
                .telephone(getTelephone())
                .enable(getEnable())
                .firstname(getUsername())
                .lastname(getNomPrenoms())
                .email(getEmail())
                .roles(getRoles().stream().toList())
                .createdDate(this.getCreationDate())
                .lastModifiedDate(this.getModificationDate())
                .build();
    }
}
