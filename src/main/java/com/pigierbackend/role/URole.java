package com.pigierbackend.role;

import java.util.HashSet;
import java.util.Set;

import com.pigierbackend.abstractentity.AbstractEntity;
import com.pigierbackend.permission.Permission;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
// import org.springframework.security.core.GrantedAuthority;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class URole extends AbstractEntity  {
    String nomRole;
    String descriptionRole;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "ROLE_PERMISSION", 
    joinColumns = @JoinColumn(name = "role_id"), 
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<Permission> permission=new HashSet<>();

    // @Override
    // public String getAuthority() {
    //     return nomRole; // Return the role name as the authority
    // }
}
