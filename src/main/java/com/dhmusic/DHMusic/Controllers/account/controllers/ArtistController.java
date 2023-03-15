package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    //inserisce un nuovo artista nel database
    @PostMapping("/create-artist")
    public Artist createArtist(@RequestBody Artist newArtist){
        //logica di creazione dell'artist
        return newArtist;
    }

    //elimina un artista nel database
    @DeleteMapping("/delete-artist")
    public void deleteArtist(@RequestBody Artist newArtist){
        //logica di rimozione dell'artist
    }

    //Aggiorna un artista nel database
    @PutMapping("/update-artist")
    public Artist updateArtist(@RequestBody Artist newArtist){
        //logica di aggiornamento dell'artist (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-artist-name), (/update-artist-surname)
        return newArtist;
    }

    //Seleziona gli artisti nel database
    @GetMapping("/get-all-artists")
    public Artist getAllArtist(){
        //logica di restituzione degli artist
        return null;
    }

    //Seleziona un artista nel database
    @GetMapping("/get-artist-by-id")
    public Artist getArtistById(){
        //logica di restituzione dell artist tramite id
        return null;
    }

}
