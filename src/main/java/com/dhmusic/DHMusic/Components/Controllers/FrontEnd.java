package com.dhmusic.DHMusic.Components.Controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.Components.repositories.content.repositories.SongRepository;
import com.dhmusic.DHMusic.Components.services.SongService;
import com.dhmusic.DHMusic.Components.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/**")
@RestController
@RequestMapping("/front")
public class FrontEnd {

    @Autowired
    SongRepository songRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    UserService userService;
    @Autowired
    SongService songService;


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
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-user-role/{id}")
    public String statusAccount(@PathVariable Long id){
        return userService.userStatus(id);

    }


    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-username/{id}")
    public String AccountUsername(@PathVariable Long id){
        return userService.accountUsername(id);
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-id")
    public String getUserId(@RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        String username= userService.decodeJWTForUsername(jwt);
        Long user= userService.foundUserIDByAccountName(username);



        return user.toString();
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/lastsSongs")
    public String getLastSongs() {
        return songService.getLast3SongsCreate();


    }








}
