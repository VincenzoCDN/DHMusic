package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Song;
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
    @PostMapping("/create")
    public Song addSong(@RequestBody Song song){   //funziona
        return songService.addSong(song);
    }
    //---------------------------------------------------------------------------------------
    @GetMapping
    public List<Song> getSongs(){           //Funziona
      return songRepository.findAll();

    }
    //---------------------------------------------------------------------------------------
    //mostrare la singola canzone
    @GetMapping("/{id}")
    public Song getSongId( @PathVariable Long id){   //Funziona
       return songRepository.findSongById(id);
    }
    //---------------------------------------------------------------------------------------
    //Mostra la singola canzone per titolo
    @GetMapping("/get/title/{title}")
    public Song getSongTitle( @PathVariable String title){   //Funziona
        return songRepository.findSongByTitle(title);
    }
    //---------------------------------------------------------------------------------------
    //elimina un Song nel database
    @DeleteMapping("/delete/{id}")                                    //Funziona
    public ResponseEntity<Void> deleteSong(@PathVariable Long id){
        songService.deleteSong(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //---------------------------------------------------------------------------------------
    //Aggiorna un Song nel database
    @PutMapping("/update/{id}")                                     //funziona se inserisco nel body "id"
    public Song updateSong (@PathVariable Long id, @RequestBody Song song) {
        return songService.updateSong(song);
    }


}
