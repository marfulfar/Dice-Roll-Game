package com.fargas.marcal.S5T2.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@Entity
@Builder
public class User  implements UserDetails {

    //TODO in Player we use String-id not integer-id
    //TODO not able to check if user table is created because of security required

    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0d2lnQCIsImlhdCI6MTY4MzIxMDAyMCwiZXhwIjoxNjgzMjIwMDQwfQ.T74PnktZueuzKdqtQerwKR7IqvxC21pe50BAaoO6bJM
    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0d2lnQCIsImlhdCI6MTY4MzIxMDIwOSwiZXhwIjoxNjgzMjIwMjI5fQ.pW8LT6cguUVDdOjB6ro8dH1ICpOt_MvWwfOr0OaRvAc

    @MongoId
    private String id;

    private String name;

    private String email;

    //With lombok we already have the getPassword method, but we overwrite it from the interface just to be clear
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
