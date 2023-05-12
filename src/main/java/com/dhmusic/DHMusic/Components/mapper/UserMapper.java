package com.dhmusic.DHMusic.Components.mapper;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder encoder;
    public User toUser(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        user.setNationality(userDTO.getNationality());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.addRoles(Roles.ROLE_REGISTERED);
        return user;
    }
}
