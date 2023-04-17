package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.mapper.SongMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongRepository songRepository; // per accesso al database
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;


    //---------------------------------------------------------------------------------------

    public void deleteSong(Long id) throws Exception {
         Song existSong = songRepository.findSongById(id);
         if(existSong != null) {
             songRepository.delete(existSong);
         }else{
             throw new RuntimeException("Song not exist!");
         }
    }
    //-----------------------------------------------------------------------
    public void deleteAll() {
        //insert conditions       TODO
      songRepository.deleteAll();
        }
    //-----------------------------------------------------------------------
    public ResponseEntity<Song> addSong(SongDTO song) throws Exception {
        Song existSong = songRepository.findSongById(song.getId());
        if (existSong != null) {
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getIdArtistOfSong() == null) {
            throw new IllegalArgumentException("Mistake! Required fields are missing");
        }
        if (artistRepository.findArtistById(song.getIdArtistOfSong()) == null){
            throw new Exception("Artist not exist!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(songRepository.save(songMapper.toSong(song)));
    }


    //-----------------------------------------------------------------------
        //aggiorna la canzone DTO
       public ResponseEntity<?> updateSong(Long id,SongDTO updateSong) {
            Song existSong = songRepository.findSongById(id); // id string o long?
            if (existSong == null) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
            } else if (!existSong.getId().equals(updateSong.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song id does not match");
            } else {
                existSong.setTitle(updateSong.getTitle());
                existSong.setGenre(updateSong.getGenre());
                existSong.setLength(updateSong.getLength());
                Artist artist = artistRepository.findArtistById(updateSong.getIdArtistOfSong());
                existSong.setArtistOfSong(artist);
                Album album = albumRepository.findAlbumById(updateSong.getIdAlbumOfSong());
                existSong.setAlbumOfSong(album);
                songRepository.save(existSong);

            }
            return ResponseEntity.ok(existSong);
        }






}





