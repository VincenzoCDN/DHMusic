package com.dhmusic.DHMusic.Components.services;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.mapper.ArtistMapper;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.ArtistRepository;

import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;

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

    /**
     * verifico se il nome dell'artista è gia esistente
     * @param artistDTO
     * @return
     */

    public boolean existArtistName(ArtistDTO artistDTO){
        boolean exist;
        Artist artist = artistRepository.findByArtistName(artistDTO.getArtistName());
        if (artist != null){
            logger.debug("exist artist");
            return exist = true;
        }
        logger.debug("not exist artist for creation new artist");
        return exist = false;
    }
    /**
     * verifico se l'id dello user esiste
     * @param artistDTO
     * @return
     */
    public boolean existUserId(ArtistDTO artistDTO){
        boolean exist;
        User userId = userRepository.findUserById(artistDTO.getUserId());
        if (userId == null){
            logger.debug("not exist User");
            return exist = false;

        }
        logger.debug("exist User");
        return exist = true;
    }

    /**
     * creazione dell'artista, verifico se ci sono i dati essenziali, poi utilizzo i metodi existArtistName e existUserId
     * per verificare se il nome dell'artista è già utilizzato e se esiste l'id dello user , in fine verifico se id dello user
     * è gia utilizzato e se non è utilizzato salva l'artista nel database
     * @param artistDTO
     * @throws Exception
     */
    public void createArtist(ArtistDTO artistDTO, Long id) throws Exception{

            artistDTO.setUserId(id);

        if( artistDTO.getArtistName() == null && artistDTO.getUserId() == null){
            logger.error("error creation artist");
            throw new Exception("you didn't put the artist");

        }
        if (artistDTO.getUserId() == null) {
            logger.error("enter the userId of the artist");
            throw new Exception("enter the userId");

        }else if (artistDTO.getArtistName() == null) {
            //logger.error("The name of the artist was not entered with id "+artistDTO);
            throw new Exception("enter the name of the artist");

        } else if (existArtistName(artistDTO)) {
            logger.error("there is already an artist with this name "+ artistDTO.getArtistName());
            throw new Exception("Exist artist Name");

        } else if (!existUserId(artistDTO)) {
            logger.error("userId does not exist "+ artistDTO.getUserId());
            throw new Exception("User id does not exist");

        } else if (artistRepository.findByUserId(artistDTO.getUserId()) != null) {
            logger.error("user tries to create another artist");
            throw new Exception("there is already an artist linked to this user ");
        }
        Artist artist = artistMapper.toArtist(artistDTO);
        artistRepository.save(artist);
        logger.info("artist creation");
    }

    /**
     * modifico un artista esistente, controlliamo se l'artista esiste tramite id, verifichiamo sempre se il nome esiste o meno
     * successivamente vedo cosa modifica utente e salvo la modifica
     * @param id
     * @param artistEditDTO
     * @return l'artista modificato
     * @throws Exception
     */

    public Artist updateArtist(Long id,ArtistDTO artistEditDTO) throws Exception {
        Artist existArtist = artistRepository.findArtistById(id);
        if(!artistRepository.existsById(id)){
            logger.warn("the artist to be modified does not exist");
            throw new Exception("the artist does not exist");

        }
        if(existArtistName(artistEditDTO)){
            logger.warn("there is already an artist with this name " +artistEditDTO.getArtistName());
            throw new Exception("there is already an artist with this name ");
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

    /**
     * elimino l'artista con un determinato id
     * @param id
     * @throws Exception
     */
    public void deleteArtist(Long id) throws Exception {
        Optional<Artist> artistOptional = artistRepository.findById(id);
        if(artistOptional.isEmpty()){
            logger.warn("Attempt to eliminate an artist who does not exist");
            throw new Exception("the artist does not exist");
        }
        Artist artist = artistOptional.get();
        User user = artist.getUser();
        user.setArtist(null);
        userRepository.save(user);
        logger.info("delete artist "+id);
        artistRepository.deleteById(id);
    }

    /**
     * vedo tutti gli user che seguono un artista con un id specifico
     * @param id
     * @return tutti gli utenti che seguono l'artista
     * @throws Exception
     */
    public List<User> getUsersFollowers(Long id) throws Exception {
        Artist existingArtist = artistRepository.findArtistById(id);
        if(existingArtist == null){
            logger.warn("artist not found");
            throw new Exception("artist not found");
        }
        logger.info("all followers of "+id+" have been seen");
        return existingArtist.getUsersFollowers();
    }

    /**
     * vedo tutti gli artisti
     * @return tutti gli artisti
     */

    public List<Artist> getAllArtist(){
        logger.info("all artists were seen");
        return artistRepository.findAll();
    }

    /**
     * controllo se esiste artista con un determinato id
     * @param id
     * @return se esiste
     */
    public boolean isValidId(Long id)  {
        boolean exists = artistRepository.existsById(id);
        if (exists == false) {
            return false;
        }
        return true;
    }

    /**
     * verifico se l'artista esiste o no e mostro l'artista con un id specifico
     * @param id
     * @return l'artista
     * @throws Exception
     */
    public Optional<Artist> getArtistById(Long id) throws Exception {
        if(!isValidId(id)){
            logger.warn("no artists were found with this id "+id);
            throw new Exception("the artist does not exist");
        }
        logger.info("The artists were seen");
        return artistRepository.findById(id);
    }
}

