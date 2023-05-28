package com.dhmusic.DHMusic.Components.Controllers.content.controllers;

import com.dhmusic.DHMusic.Components.entities.content.entities.Album;
import com.dhmusic.DHMusic.Components.entities.content.entities.AlbumDTO;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.dhmusic.DHMusic.Components.services.AlbumService;
import com.dhmusic.DHMusic.Components.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000/")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
    public ResponseEntity createAlbum(@RequestBody AlbumDTO albumDTO){
        try {
            albumService.createAlbum(albumDTO);
            return ResponseEntity.ok(albumDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------
    //Elimina un Song nel database

    @DeleteMapping("/delete-album/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
    public Album updateAlbum(@RequestBody Album newAlbum){
        //logica di aggiornamento dell'Album (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Album-name), (/update-Album-surname)

        // L ho lasciato cosi perché penso sia meglio per ogni attributo.
        return newAlbum;
    }
    //---------------------------------------------------------------------------------------
    //Update the Title
    @PutMapping("/update-title-album")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ACTIVE')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ACTIVE')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ARTIST')")
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
