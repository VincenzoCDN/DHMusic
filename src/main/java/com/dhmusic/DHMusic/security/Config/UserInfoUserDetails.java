package com.dhmusic.DHMusic.security.Config;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    // "ADMIN, REGISTERED, EDITOR"

    public UserInfoUserDetails(Long id, String username, String password, List<Roles> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = Arrays.asList(roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).toArray(SimpleGrantedAuthority[]::new));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId(){
        return id;
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
