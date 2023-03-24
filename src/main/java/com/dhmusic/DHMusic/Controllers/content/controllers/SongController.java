package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import com.dhmusic.DHMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService; // per accesso al database
    private SongRepository songRepository;


    //---------------------------------------------------------------------------------------
    @PostMapping("/create-song")
    public Song addSong(@RequestBody Song song){
        return songService.addSong(song);
    }
    //---------------------------------------------------------------------------------------
    @GetMapping
    public List<Song> getSongs(){
      return songRepository.findAll();

    }
    //---------------------------------------------------------------------------------------
    //mostrare la singola canzone
    @GetMapping("/get")
    public Song getSongId( @PathVariable long id){
       return songRepository.findSongById(id);
    }
    //---------------------------------------------------------------------------------------
    //elimina un Song nel database
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSong(@RequestBody Song song){
        songService.deleteSong(song);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //---------------------------------------------------------------------------------------
    //Aggiorna un Song nel database
   @PutMapping("/update-Song")
    public ResponseEntity<Song> updateSong(@PathVariable String id,@RequestBody Song songDetail){
        Song updateSong = songService.updateSong(id,songDetail);
        return ResponseEntity.ok().body(updateSong);
    }

   /*@GetMapping("/get")
    public Song getSongId( @PathVariable Song title){
        return songService.searchSong(title);
    }

    */
}
