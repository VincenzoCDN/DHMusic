package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.repository.account_repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public void createArtist(Artist artist) throws Exception{
        if(artist == null){
            throw new Exception("you didn't put the artist");
        }
        artistRepository.save(artist);
    }

    public Artist updateArtist(Artist artist) throws Exception {
        Artist existingArtist = artistRepository.findByArtistName(artist.getArtistName());
        if(existingArtist == null){
            throw new Exception("artist inesistente");
        }
        existingArtist.setBio(artist.getBio());
        existingArtist.setAlbumsOfArtist(artist.getAlbumsOfArtist());
        existingArtist.setSongOfArtist(artist.getSongOfArtist());
        return artistRepository.save(existingArtist);
    }

    public void deleteArtist(Long id){
        artistRepository.deleteById(id);
    }

    /*public List<User> getUsersFollowers(Artist artist){
        Artist existingArtist = artistRepository.findByArtistName(artist.getName());
        if( existingArtist == null){
            return null;
        }
        return existingArtist.getUsersFollowers();
    }

     */



    public List<Artist> getAllArtist(){
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(Long id){
        return artistRepository.findById(id);
    }

}

