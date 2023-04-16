package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.DhMusicApplication;
import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.mapper.ArtistMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;

import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    Logger logger = LoggerFactory.getLogger(ArtistService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistMapper artistMapper;

    public boolean existArtistName(ArtistDTO artistDTO){
        boolean exist;
        Artist artist = artistRepository.findByArtistName(artistDTO.getArtistName());
        if (artist != null){
            logger.error("exist artist");
            return exist = true;
        }
        logger.info("not exist artist for creation new artist");
        return exist = false;
    }

    public boolean existUserId(ArtistDTO artistDTO){
        boolean exist;
        User userId = userRepository.findUserById(artistDTO.getUserId());
        if (userId != null){
            logger.error("exist artist");
            return exist = true;
        }
        logger.info("not exist artist");
        return exist = false;
    }


    public void createArtist(ArtistDTO artistDTO) throws Exception{
        if(artistDTO == null){
            logger.error("error creation artist");
            throw new Exception("you didn't put the artist");

        } else if (artistDTO.getArtistName() == null) {
            logger.error("enter the name of the artist");
            throw new Exception("enter the name of the artist");

        } else if (existArtistName(artistDTO) == true) {
            logger.error("exist artist");
            throw new Exception("Exist artist");

        } else if (artistDTO.getUserId()==null) {
            throw new Exception("User id does not exist");

        } else if (existUserId(artistDTO) == true) {
            logger.error("user tries to create another artist");
            throw new Exception("there is already an artist linked to this user ");
        }
        Artist artist = artistMapper.toArtist(artistDTO);
        artistRepository.save(artist);
        logger.info("artist creation");
    }

    public Artist updateArtist(Long id,ArtistDTO artistEditDTO) throws Exception {
        Artist existArtist = artistRepository.findArtistById(id);
        if(!artistRepository.existsById(id)){
            logger.warn("the artist to be modified does not exist");
            throw new Exception("the artist does not exist");

        }
        if (artistEditDTO.getArtistName() == null && artistEditDTO.getBio() == null && artistEditDTO.getUserId() == null ) {

            logger.info("the artist"+ existArtist+ "has not been modified");
            return existArtist;

        } else if (artistEditDTO.getBio() == null) {
            logger.info("the artist changed only the name");
            existArtist.setArtistName(artistEditDTO.getArtistName());
            return artistRepository.save(existArtist);

        } else if (artistEditDTO.getArtistName() == null) {
            logger.info("the artist changed only the bio");
            existArtist.setBio(artistEditDTO.getBio());
            return artistRepository.save(existArtist);

        }
        else {
            existArtist.setArtistName(artistEditDTO.getArtistName());
            existArtist.setBio(artistEditDTO.getBio());
            logger.info("the artist has been modified");

            return artistRepository.save(existArtist);
        }
    }

    public void deleteArtist(Long id) throws Exception {
        if(!artistRepository.existsById(id)){
            logger.warn("Attempt to eliminate an artist who does not exist");
            throw new Exception("the artist does not exist");
        }
        logger.info("delete artist "+id);
        artistRepository.deleteById(id);
    }

    public List<User> getUsersFollowers(Long id) throws Exception {
        Artist existingArtist = artistRepository.findArtistById(id);
        if(existingArtist == null){
            logger.warn("artist not found");
            throw new Exception("artist not found");
        }
        logger.info("all followers of "+id+" have been seen");
        return existingArtist.getUsersFollowers();
    }


    public List<Artist> getAllArtist(){
        logger.info("all artists were seen");
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
            logger.warn("no artists were found with this id "+id);
            throw new Exception("the artist does not exist");
        }
        logger.info("The artists were seen");
        return artistRepository.findById(id);
    }
}

