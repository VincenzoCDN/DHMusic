package com.dhmusic.DHMusic.Components.mapper;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    @Autowired
    UserRepository userRepository;

    public Artist toArtist(ArtistDTO artistDTO){
        Artist artist = new Artist();
        artist.setArtistName(artistDTO.getArtistName());
        artist.setBio(artistDTO.getBio());
        User user = userRepository.findUserById(artistDTO.getUserId());
        artist.setUser(user);
        return artist;
    }

    public Artist toArtistEdit(ArtistDTO artistDTO){
        Artist artist = new Artist();
        artist.setId(artistDTO.getId());
        artist.setArtistName(artistDTO.getArtistName());
        artist.setBio(artistDTO.getBio());
        return artist;
    }


    public ArtistDTO toArtistDto(Artist artist){
        ArtistDTO artistDTO= new ArtistDTO();
        artist.getArtistName();
        artist.getBio();
        return artistDTO;
    }


}
