package com.dhmusic.DHMusic.Components.Controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.Components.repositories.content.repositories.SongRepository;
import com.dhmusic.DHMusic.Components.services.SongService;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.security.Auth.Entities.LoginDTO;
import com.dhmusic.DHMusic.security.Auth.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/**")
@RestController
@RequestMapping("/front2/")
public class FrontEndTwo {

    @Autowired
    SongRepository songRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    UserService userService;
    @Autowired
    SongService songService;



    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/play/{id}")
    public String getpla(@PathVariable Long id){
        String link= songService.playSong(id);

        return "https://www.youtube.com/embed/" + link;

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

    @PutMapping("/verificate_code/{id}")
    public ResponseEntity<?> verificateUser(@PathVariable Long id, @RequestParam String code){
        try {
            return ResponseEntity.accepted().body(userService.verificareAccount(id, code));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
        return userService.createUser2(newUser);
    }
}
