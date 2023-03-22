package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.services.ArtistService;
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

    //inserisce un nuovo artista nel database
    @PostMapping("/create-artist")
    public ResponseEntity createArtist(@RequestBody Artist newArtist){
        try {
            artistService.createArtist(newArtist);
            return ResponseEntity.ok(newArtist);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }

    }

    //elimina un artista nel database
    @DeleteMapping("/delete-artist/{id}")
    public ResponseEntity deleteArtist(@PathVariable Long id){
        try {
            artistService.deleteArtist(id);
            return ResponseEntity.accepted().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    //Aggiorna un artista nel database
    @PutMapping("/update-artist/{id}")
    public ResponseEntity updateArtist(@PathVariable Long id,@RequestBody Artist newArtist){
        try {

            newArtist = artistService.updateArtist(id,newArtist);
            return ResponseEntity.ok().body(newArtist);
        } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Seleziona gli artisti nel database
    @GetMapping("/get-all-artists")
    public ResponseEntity getAllArtist(){
        try {
            List<Artist> artist = artistService.getAllArtist();
            return ResponseEntity.ok(artist);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Seleziona un artista nel database
    @GetMapping("/get-artist-by-id/{id}")
    public ResponseEntity getArtistById(@PathVariable Long id){
        try{
            Optional<Artist> artist = artistService.getArtistById(id);
            return ResponseEntity.ok(artist);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /*@GetMapping("/get-user-followers")
    public List<User> getFollowers(@RequestParam Artist artist){
        return artistService.getUsersFollowers(artist);
    }
     */
}

