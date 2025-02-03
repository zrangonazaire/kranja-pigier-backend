package com.pigierbackend.role;

import java.util.List;

import com.pigierbackend.abstractentity.AbstractEntity;
import com.pigierbackend.privilege.Privilege;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROLE")
@Getter
@Setter
public class Role extends AbstractEntity implements GrantedAuthority {
    String nomRole;
    String descriptionRole;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "ROLE_PRIVILEGE", 
    joinColumns = @JoinColumn(name = "role_id"), 
    inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    List<Privilege> privileges;

    @Override
    public String getAuthority() {
        return nomRole; // Return the role name as the authority
    }
}
