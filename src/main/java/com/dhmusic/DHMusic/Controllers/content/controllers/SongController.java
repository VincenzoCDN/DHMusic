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

    @PostMapping("/create")                                     //funziona
    public ResponseEntity<?> createSong(@RequestBody SongDTO songDTO) throws Exception {
        try {
            songService.addSong(songDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("The song was created!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    //---------------------------------------------------------------------------------------
    //Aggiorna un Song nel database DTO
    @PutMapping("/update/{id}")                               //funziona
    public ResponseEntity<?> updateSong(@PathVariable Long id, @RequestBody SongDTO song) {
        try {
            songService.updateSong(id, song);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
        //---------------------------------------------------------------------------------------

    @GetMapping("/play/{id}")
    public ResponseEntity<?> playSong(@PathVariable Long id) {
        try {
            Song song = songRepository.findSongById(id);

            return ResponseEntity.accepted().body("https://www.youtube.com/watch?v=" + song.getLink());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/play2/{id}")
    public ResponseEntity<?> playSong2(@PathVariable Long id) {   //Funziona
        try {
            Song song = songRepository.findSongById(id);
            String strID= song.getLink();

            String str1= String.format("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/%s\"" +
                    " title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                    "encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>", strID);


            return ResponseEntity.accepted().body(str1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}