package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.Controllers.account.controllers.ArtistController;
import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.entities.content.entities.PlaylistDTO;
import com.dhmusic.DHMusic.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    @PostMapping("/create-playlist")
    public ResponseEntity<?> createNewPlaylist(@RequestBody PlaylistDTO playlistDTO){
        try {
            playlistService.createPlaylist(playlistDTO);
            logger.info("creation new playlist");
            return ResponseEntity.ok("create playlist");

        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("get-all-playlist")
    public ResponseEntity<List<Playlist>> getAllPlaylist(){
        List<Playlist> playlists = playlistService.getAllPlaylist();
        logger.info("all playlist were seen");
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("get-playlist-by-id/{id}")
    public ResponseEntity<?> getOnePlaylist(@PathVariable Long id){
        try{
            Playlist playlist = playlistService.getById(id);
            logger.info("the playlist were seen");
            return ResponseEntity.ok(playlist);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("delete-playlist/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id){
        try {
            playlistService.deletePlaylist(id);
            logger.info("the playlist whit id "+id+" has been delete");
            return ResponseEntity.accepted().body("the playlist has been delete");
        } catch (Exception e) {
            logger.info("error in deleting the playlist");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update-playlist/{id}")
    public ResponseEntity<?> updateOnePlaylist(@PathVariable Long id, @RequestBody Playlist playlist){
        try {
            playlistService.updatePlaylist(id,playlist);
            logger.info("an playlist with id "+ id + " has been modified");
            return ResponseEntity.ok("modified playlist");
        } catch (Exception e) {
            logger.error("error in the modification of the playlist with id "+ id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("add-song-to-playlist")
    public ResponseEntity<?> addSongToPlaylist(@RequestParam Long playlistId,@RequestParam Long songId){
        try {
            playlistService.addSongToPlaylist(playlistId,songId);
            logger.info("the song with id "+songId+" add to playlist with id %d " + playlistId);
            return ResponseEntity.ok("and song to playlist");
        } catch (Exception e) {
            logger.error("could not add the song with id " +songId+"  to the playlist with id  " + playlistId);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
