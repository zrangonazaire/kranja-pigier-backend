package com.pigierbackend.utilisateur;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
Long id;
    String username;
    String password;
    String nomPrenoms;
    String telephone;
    String email;
    Set<Long> roleIds;
    public Utilisateur toUtilisateur() {
        
        Utilisateur utilisateur =  Utilisateur.builder()
            
                .username(this.username)
                .password(this.password)
                .nomPrenoms(this.nomPrenoms)
                .telephone(this.telephone)
                .email(this.email)
                .build();
       
        return utilisateur;
    }
}
