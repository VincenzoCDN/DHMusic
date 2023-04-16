package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import com.dhmusic.DHMusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService; // per accesso al database
    @Autowired
    private SongRepository songRepository;



    //---------------------------------------------------------------------------------------
    //mostra lista di canzoni
    @GetMapping
    public List<Song> getSongs() {           //Funziona
        return songRepository.findAll();

    }

    //---------------------------------------------------------------------------------------
    //mostrare la singola canzone
    @GetMapping("/{id}")
    public Song getSongId(@PathVariable Long id) {             //Funziona
        Song existSong = songRepository.findSongById(id);
        if (existSong == null) {
            throw new RuntimeException("Song not exist!");
        }
        return songRepository.findSongById(id);
    }

    //---------------------------------------------------------------------------------------
    //Mostra la singola canzone per titolo
    @GetMapping("/get/title/{title}")
    public Song getSongTitle(@PathVariable String title) {   //Funziona
        return songRepository.findSongByTitle(title);
    }

    //---------------------------------------------------------------------------------------
    //elimina un Song nel database
    @DeleteMapping("/delete/{id}")                                    //Funziona
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) throws Exception {
        songService.deleteSong(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //---------------------------------------------------------------------------------------
    //elimina tutto
    @DeleteMapping("/delete/all")            //funziona
    public void deleteAll() {
        songService.deleteAll();
    }


    //---------------------------------------------------------------------------------------
    //Aggiunge (uso DTO)

    @PostMapping("/create-song")                                     //funziona
    public ResponseEntity<?> createSong(@RequestBody SongDTO songDTO) throws Exception {
        try {
            songService.addSong(songDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }

    }

    //---------------------------------------------------------------------------------------
    //Aggiorna un Song nel database DTO
    @PutMapping("/update-song/{id}")                               //funziona
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody SongDTO song) {
        try {
            songService.updateSong(id, song);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }
        //---------------------------------------------------------------------------------------


    }
}