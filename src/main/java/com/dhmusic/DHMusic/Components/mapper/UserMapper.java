package com.dhmusic.DHMusic.Components.mapper;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(UserService.hashPassword(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        user.setNationality(userDTO.getNationality());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        return user;
    }
}
