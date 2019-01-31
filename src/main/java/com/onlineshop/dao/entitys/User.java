package com.onlineshop.dao.entitys;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min = 1)
    private String username;

    @NonNull
    @Size(min = 1)
    private String fullname;

    @NonNull
    @Size(min = 1)
    private String password;

    @NonNull
    @Size(min = 1)
    @Pattern(regexp = "([A-z0-9_.-]{1,})@([A-z0-9_.-]{1,}).([A-z]{2,8})")
    private String email;

    private boolean active;

    public User(){

    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * User details.
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
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
        return isActive();
    }
}