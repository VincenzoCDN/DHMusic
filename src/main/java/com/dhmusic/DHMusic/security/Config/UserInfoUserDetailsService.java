package com.dhmusic.DHMusic.security.Config;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional= userRepository.findUserByUsername(username);
        User user = optional.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }
}
