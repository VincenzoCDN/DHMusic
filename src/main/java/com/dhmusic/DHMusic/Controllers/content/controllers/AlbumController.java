package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.services.AlbumService;
import com.dhmusic.DHMusic.services.SongService;
import jakarta.websocket.server.PathParam;
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

    @Autowired
    SongService songService;








    //---------------------------------------------------------------------------------------
    //Crea un Album nel database
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

    //---------------------------------------------------------------------------------------
    //Elimina un Song nel database

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

    //---------------------------------------------------------------------------------------
    //                              Aggiorna il Database:
    //Update Generale
    @PutMapping("/update-album")
    public Album updateAlbum(@RequestBody Album newAlbum){
        //logica di aggiornamento dell'Album (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Album-name), (/update-Album-surname)

        // L ho lasciato cosi perché penso sia meglio per ogni attributo.
        return newAlbum;
    }
    //---------------------------------------------------------------------------------------
    //Update the Title
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
    //---------------------------------------------------------------------------------------
    //Update the Artist(s)
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

    //---------------------------------------------------------------------------------------
    //                          Found Albums in the database:
    //All Album (No songs)
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

    //---------------------------------------------------------------------------------------
    //By Id
    @GetMapping("/get-album-by-id")
    public ResponseEntity getAlbumById(@RequestParam long id){
        try{
            Optional<Album> album = albumService.getAlbumById(id);
            List<Song> song= albumService.getSongByAlbumId(id);

            return ResponseEntity.ok(song);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------
    //By Tittle
    @GetMapping("/get-album-by-title")
    public ResponseEntity getAlbumByTitle(@RequestParam String title){
        try{

            List<Song> song= albumService.getSongByAlbumTitle(title);

            return ResponseEntity.ok(song);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------
    //                                 Add Song in the Album
    // Add by: Id song + Id Album
    @PutMapping("/add-music-in-the-album")
    public ResponseEntity addMusic(@RequestParam long idSong, @RequestParam long idAlbum) {
        try {

            albumService.addSongsInTheAlbum(idSong, idAlbum);
            return ResponseEntity.ok(albumService.getAlbumById(idAlbum));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

}
