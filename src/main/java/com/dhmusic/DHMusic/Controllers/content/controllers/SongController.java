package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import com.dhmusic.DHMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService; // per accesso al database
    private final SongRepository songRepository;

    public SongController(SongService songService,
                          SongRepository songRepository){
        this.songService=songService;
        this.songRepository = songRepository;
    }
    @PostMapping("/create-song")
    public Song addSong(@RequestBody Song song){
        return songService.addSong(song);
    }
    @GetMapping
    public List<Song> getSongs(){
        return songRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Song> getUsersId(@PathVariable String id){
        return songRepository.findById(Integer.valueOf(id));
    }

    //elimina un Song nel database
    @DeleteMapping("/delete-song")
    public ResponseEntity<Void> deleteSong(@RequestBody Song song){
        songService.deleteSong(song);
        return ResponseEntity.status(HttpStatus.OK).build();

    }


    //Aggiorna un Song nel database
   /* @PutMapping("/update-Song")
    public ResponseEntity<Song> updateSong(@PathVariable String id,@RequestBody Song songDetail){
        //logica di aggiornamento dell'Song (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Song-name), (/update-Song-surname)


    }

    */

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
