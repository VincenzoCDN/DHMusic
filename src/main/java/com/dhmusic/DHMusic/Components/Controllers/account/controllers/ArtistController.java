package com.dhmusic.DHMusic.Components.Controllers.account.controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.Components.services.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    ArtistService artistService; //istanzio ArtistServer

    Logger logger = LoggerFactory.getLogger(ArtistController.class);

    /**
     * inserisce un nuovo artista nel database
     * @param artistDTO
     * @return restituisce lo status più un messaggio
     */
    @PostMapping("/create-artist")
    public ResponseEntity createArtist(@RequestBody ArtistDTO artistDTO){
        try {

            artistService.createArtist(artistDTO);
            logger.info("a new artist was created");
            return ResponseEntity.ok("create artist ");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("there was an error in creating a new artist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * elimina un artista nel database
     * @param id
     * @return restituisce lo status
     */
    @DeleteMapping("/delete-artist/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id){
        try {
            artistService.deleteArtist(id);
            logger.info("one artist was eliminated");
            return ResponseEntity.accepted().build();
        }catch (Exception e){
            logger.info("error in deleting the artist");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Aggiorna un artista nel database
     * @param id
     * @param newArtist
     * @return restituisce lo status più un messaggio
     */
    @PutMapping("/update-artist/{id}")
    public ResponseEntity updateArtist(@PathVariable Long id,@RequestBody ArtistDTO newArtist){
        try {
            artistService.updateArtist(id,newArtist);
            logger.info("an artist has been modified");
            return ResponseEntity.ok().body("modified artist");
        } catch (Exception e) {
            logger.error("error in the modification of the artist");
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * visualizza gli artisti nel database
     * @return un messaggio di status più tutti gli artisti
     */
    @GetMapping("/get-all-artists")
    public ResponseEntity getAllArtist(){
        try {
            List<Artist> artist = artistService.getAllArtist();
            logger.info("all artists were seen");
            return ResponseEntity.ok(artist);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error get all artist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * visualizza un artista nel database
     * @param id
     * @return un messaggio di status più un artisti
     */
    @GetMapping("/get-artist-by-id/{id}")
    public ResponseEntity getArtistById(@PathVariable Long id){
        try{
            Optional<Artist> artist = artistService.getArtistById(id);
            logger.info("the artists were seen");
            return ResponseEntity.ok(artist);
        }catch (Exception e){
            logger.error("error in the artist's vision");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * visualizza i followers di un artista con un determinato id
     * @param id
     * @return un messaggio di status più tutti i followers
     */
    @GetMapping("/get-user-followers")
    public ResponseEntity getFollowers(@RequestParam Long id){
        try {
            artistService.getUsersFollowers(id);
            logger.info("all followers of " +id+ " have been seen\"");
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            logger.error("artist not found");
            throw new RuntimeException(e);
        }

    }
}

