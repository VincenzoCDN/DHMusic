package com.dhmusic.DHMusic.Components.mapper;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ArtistMapper {

    @Autowired
    UserRepository userRepository;

    public Artist toArtist(ArtistDTO artistDTO){
        Artist artist = new Artist();
        artist.setArtistName(artistDTO.getArtistName());
        artist.setBio(artistDTO.getBio());
        User user = userRepository.findUserById(artistDTO.getUserId());
        user.addRoles(Roles.ROLE_ARTIST);
        artist.setUser(user);
        return artist;
    }


}
