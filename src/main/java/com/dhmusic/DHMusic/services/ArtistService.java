package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.mapper.ArtistMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    ArtistMapper artistMapper;


    public void createArtist(ArtistDTO artistDTO) throws Exception{
        if(artistDTO == null){
            throw new Exception("you didn't put the artist");
        }
        Artist artist = artistMapper.toArtist(artistDTO);
        artistRepository.save(artist);
    }

    public Artist updateArtist(Long id,ArtistDTO artistEditDTO) throws Exception {

        if(!artistRepository.existsById(id)){
            throw new Exception("artist inesistente");
        }


        artistEditDTO.setId(id);
        artistEditDTO.setArtistName(artistEditDTO.getArtistName());
        artistEditDTO.setBio(artistEditDTO.getBio());

        return artistRepository.save(artistMapper.toArtistEdit(artistEditDTO));
    }


    public void deleteArtist(Long id){
        artistRepository.deleteById(id);
    }

    public List<User> getUsersFollowers(String artistName){
        Artist existingArtist = artistRepository.findByArtistName(artistName);
        if( existingArtist == null){
            return null;
        }
        return existingArtist.getUsersFollowers();
    }


    public List<Artist> getAllArtist(){
        return artistRepository.findAll();
    }

    public boolean isValidId(Long id)  {
        boolean exists = artistRepository.existsById(id);
        if (exists == false) {
            return false;
        }
        return true;
    }

    public Optional<Artist> getArtistById(Long id) throws Exception {
        if(isValidId(id) == false){
            throw new Exception("l'id dell'artista non esiste");
        }
        return artistRepository.findById(id);
    }
}

