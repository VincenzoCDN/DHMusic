package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createArtist(@RequestBody Artist newArtist){
        try {
            artistService.createArtist(newArtist);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //elimina un artista nel database
    @DeleteMapping("/delete-artist/{id}")
    public void deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
    }

    //Aggiorna un artista nel database
    @PutMapping("/update-artist/{id}")
    public Artist updateArtist(@PathVariable Long id,@RequestBody Artist newArtist) throws Exception {

        return artistService.updateArtist(newArtist);
    }

    //Seleziona gli artisti nel database
    @GetMapping("/get-all-artists")
    public List<Artist> getAllArtist(){

        return  artistService.getAllArtist();
    }

    //Seleziona un artista nel database
    @GetMapping("/get-artist-by-id")
    public Optional<Artist> getArtistById(@RequestParam Long id){

        return artistService.getArtistById(id);
    }

    /*@GetMapping("/get-user-followers")
    public List<User> getFollowers(@RequestParam Artist artist){
        return artistService.getUsersFollowers(artist);
    }

     */
}

