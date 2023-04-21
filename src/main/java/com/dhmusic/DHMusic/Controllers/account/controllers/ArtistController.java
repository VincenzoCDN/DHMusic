package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.DhMusicApplication;
import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.account.entities.ArtistDTO;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.services.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    ArtistService artistService; //istanzio ArtistServer

    Logger logger = LoggerFactory.getLogger(ArtistController.class);

    //inserisce un nuovo artista nel database
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

    //elimina un artista nel database
    @DeleteMapping("/delete-artist/{id}")
    public ResponseEntity deleteArtist(@PathVariable Long id){
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

    //Aggiorna un artista nel database
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


    //Seleziona gli artisti nel database
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

    //Seleziona un artista nel database
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

    @PostMapping("/upload-profile/{artistId}")
    public ResponseEntity uploadProfilePicture(@PathVariable Long artistId, @RequestParam MultipartFile[] profilePictures) {
        if (profilePictures.length == 0) {
            return ResponseEntity.noContent().build();
        }
        else if (profilePictures.length > 1) {
            return ResponseEntity.badRequest().body("Too much profile pictures, please upload only one");
        }
        try {
            logger.info("Uploading profile picture for user " + artistId);
            // upload the single profile picture into the hard disk
            // and write its partial path into correspondent user entity
            artistService.uploadProfilePictureArtist(artistId,profilePictures[0]);
            return ResponseEntity.status(HttpStatus.OK).body("file uploaded");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/download-ProfilePic/{artistId}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity viewProfilePicture(@PathVariable Long artistId){
        try {
            logger.info("Requested profile picture for artist: " + artistId);
            return ResponseEntity.ok(artistService.getUserProfilePicture(artistId));
        }catch (Exception e){
            logger.warn("internal error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

