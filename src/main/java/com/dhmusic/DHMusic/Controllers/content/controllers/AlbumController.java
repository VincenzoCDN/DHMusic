package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    //inserisce un nuovo Album nel database
    @PostMapping("/create-album")
    public ResponseEntity createAlbum(@RequestBody Album album){
        try {
            albumService.createAlbum(album);
            return ResponseEntity.ok(album);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }
    }

    //elimina un Album nel database
    @DeleteMapping("/delete-album/{id}")
    public ResponseEntity deleteAlbum(@RequestBody Long id){
        try {
           albumService.deleteAlbum(id);
            return ResponseEntity.accepted().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    //Aggiorna un Album nel database
    @PutMapping("/update-album")
    public Album updateAlbum(@RequestBody Album newAlbum){
        //logica di aggiornamento dell'Album (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Album-name), (/update-Album-surname)

        // L ho lasciato cosi perché penso sia meglio per ogni attributo.
        return newAlbum;
    }

    @PutMapping("/update-title-album")
    public ResponseEntity<Object> updateAlbumTitile(@RequestBody Album newAlbum){
      try {
          albumService.updateTitile(newAlbum);
          return ResponseEntity.accepted().build();

      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
      }
    }

    @PutMapping("/update-artist-album")
    public ResponseEntity<Object> updateAlbumArtist(@RequestBody Album newAlbum){
        try {
            albumService.updateArtist(newAlbum);
            return ResponseEntity.accepted().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    //Seleziona gli Album nel database
    @GetMapping("/get-all-albums")
    public ResponseEntity getAllAlbum(){
        try {
            List<Album> albums = albumService.getAllAlbum();
            return ResponseEntity.ok(albums);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Seleziona un Album nel database
    @GetMapping("/get-album-by-id")
    public ResponseEntity getAlbumById(@PathVariable Long id){
        try{
            Optional<Album> album = albumService.getAlbumById(id);
            return ResponseEntity.ok(album);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /*@PutMapping("/album-add-music")
    public ResponseEntity addMusic(Song song, Album album){
        try{
            Optional<Album> album = albumService.getAlbumById(al);
            return ResponseEntity.ok(album);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

}
