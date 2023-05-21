package com.dhmusic.DHMusic.Components.Controllers.content.controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.dhmusic.DHMusic.Components.services.SongService;
import com.dhmusic.DHMusic.Components.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.Components.repositories.content.repositories.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:3000/**")
@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService; // per accesso al database
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    Logger logger = LoggerFactory.getLogger(SongController.class);



    //---------------------------------------------------------------------------------------
    //mostra lista di canzoni
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping
    public List<Song> getSongs() {           //Funziona
        return songRepository.findAll();

    }

    //---------------------------------------------------------------------------------------
    //mostrare la singola canzone
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/{id}")
    public Song getSongId(@PathVariable Long id) {             //Funziona
        Song existSong = songRepository.findSongById(id);
        if (existSong == null) {
            throw new RuntimeException("Song not exist!");
        }
        return songRepository.findSongById(id);
    }

    /**
     * mostra la singola canzone per titolo
     * @param title come @PathVariable
     * @return canzone trovata all'interno della repository con lo stesso titolo
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/get/title/{title}")
    public Song getSongTitle(@PathVariable String title) {   //Funziona
        return songRepository.findSongByTitle(title);
    }

    //------------------------------------------------------------------------------------------------------

    /**
     * Elimina una canzone all'interno del database.
     * @param id come @PathVariable.
     * @return salvataggio dell'eliminazione avvenuta.
     * @throws Exception Ok se la canzone è stata eliminata con successo.
     */

    @DeleteMapping("/delete/{id}")                                    //Funziona
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) throws Exception {
        songService.deleteSong(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //------------------------------------------------------------------------------------------------------
    /**
     * Elimina tutte le canzoni presenti all'interno del database
     */
    @DeleteMapping("/delete/all")            //funziona
    public void deleteAll() {
        songService.deleteAll();
    }


    //------------------------------------------------------------------------------------------------------

    /**
     * Aggiunge una nuova canzone
     * @param songDTO come @RequestBody
     * @return lo stato "Created" con messaggio che la canzone è stata creata con successo.
     * @throws Exception Bad Request con messaggio dell'eventuale errore presente.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @PostMapping("/create")
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

    /**
     * Aggiorna una canzone presente all'interno del database
     * @param id come @PathVariable
     * @param song come @RequestBody
     * @return se l'aggiornamento è stato accettato o no tramite una Bad request.
     */
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

    /**
     * Mostra la singola canzone richiesta
     * @param id come @PathVariable
     * @return i dati della canzone richiesta o un "Not found" nel caso in cui la canzone non viene trovata
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/get/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id){
        try {
            songService.getSongById(id);
            Song existSong = songRepository.findSongById(id);

            return ResponseEntity.ok(existSong);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    //------------------------------------------------------------------------------------------------------
/*
    /**
     * Mostra solo iil titolo e l'artista della canzone.
     * @param id come @PathVariable.
     * @return titolo + artista.
     */
    /*
    @GetMapping("/{id}")
    public ResponseEntity<String>getSongByIdWithArtist(@PathVariable Long id) {
        try {
            String existSong = songService.getSongByIdWithArtist(id);
            return ResponseEntity.status(HttpStatus.OK).body(existSong);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/
    //------------------------------------------------------------------------------------------------------
    // FOR THE FRONT END
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/play/{id}")
    public String playSong(@PathVariable Long id) {
        try {
            Song song = songRepository.findSongById(id);

            return "https://www.youtube.com/embed/" + song.getLink();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/getTitile/{id}")
    public String getTitleByID(@PathVariable Long id) {
        try {
            Song song = songRepository.findSongById(id);

            return song.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/getArtistName/{id}")
    public String getArtistNameByIDSong(@PathVariable Long id) {
        try {
            Song song = songRepository.findSongById(id);
            Artist artist = artistRepository.findArtistById(song.getArtistOfSong().getId());

            return artist.getArtistName();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/getSongsRandomByArtist")
    public String getSongsRandomByArtist(){
       return songService.random3SongOfRandomArtist();

    }
    @CrossOrigin(origins = "http://localhost:3000/**")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_REGISTERED')")
    @GetMapping("/getSongsByGenre")
    public String getSongsRandomByGenre(){
        return songService.getSongsRandomByGenre();

    }


}