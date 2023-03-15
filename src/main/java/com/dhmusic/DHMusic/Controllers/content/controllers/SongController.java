package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/songs")
public class SongController {
    
    //inserisce un nuovo Song nel database
    @PostMapping("/create-Song")
    public Song createSong(@RequestBody Song newSong){
        //logica di creazione dell'Song
        return newSong;
    }

    //elimina un Song nel database
    @DeleteMapping("/delete-Song")
    public void deleteSong(@RequestBody Song newSong){
        //logica di rimozione dell'Song
    }

    //Aggiorna un Song nel database
    @PutMapping("/update-Song")
    public Song updateSong(@RequestBody Song newSong){
        //logica di aggiornamento dell'Song (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Song-name), (/update-Song-surname)
        return newSong;
    }

    //Seleziona gli Song nel database
    @GetMapping("/get-all-Songs")
    public Song getAllSong(){
        //logica di restituzione degli Song
        return null;
    }

    //Seleziona un Song nel database
    @GetMapping("/get-Song-by-id")
    public Song getSongById(){
        //logica di restituzione dell Song tramite id
        return null;
    }

}
