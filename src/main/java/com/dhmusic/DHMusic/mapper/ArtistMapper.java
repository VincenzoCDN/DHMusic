package com.dhmusic.DHMusic.mapper;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.account.entities.ArtistDTO;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public Artist toArtist(ArtistDTO artistDTO){
        Artist artist = new Artist();
        artist.setArtistName(artistDTO.getArtistName());
        artist.setBio(artistDTO.getBio());
        artist.setUser(artistDTO.getUserId());
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
