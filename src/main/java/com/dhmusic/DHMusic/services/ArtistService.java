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

    public boolean existArtistName(ArtistDTO artistDTO){
        boolean exist;
        Artist artist = artistRepository.findByArtistName(artistDTO.getArtistName());
        if (artist != null){
            return exist = true;
        }
        return exist = false;
    }

    public void createArtist(ArtistDTO artistDTO) throws Exception{
        if(artistDTO == null){
            throw new Exception("you didn't put the artist");
        } else if (existArtistName(artistDTO) == true) {
            throw new Exception("Exist artist");
        }
        Artist artist = artistMapper.toArtist(artistDTO);
        artistRepository.save(artist);
    }

    public Artist updateArtist(Long id,ArtistDTO artistEditDTO) throws Exception {
        Artist existArtist = artistRepository.findArtistById(id);
        if(!artistRepository.existsById(id)){
            throw new Exception("the artist does not exist");

        } else if (artistEditDTO.getBio() == null) {
            existArtist.setArtistName(artistEditDTO.getArtistName());
            return artistRepository.save(existArtist);

        } else if (artistEditDTO.getArtistName() == null) {
            existArtist.setBio(artistEditDTO.getBio());
            return artistRepository.save(existArtist);

        } else {
            existArtist.setArtistName(artistEditDTO.getArtistName());
            existArtist.setBio(artistEditDTO.getBio());

            return artistRepository.save(existArtist);
        }
    }


    public void deleteArtist(Long id) throws Exception {
        if(!artistRepository.existsById(id)){
            throw new Exception("the artist does not exist");
        }
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
            throw new Exception("the artist does not exist");
        }
        return artistRepository.findById(id);
    }
}

