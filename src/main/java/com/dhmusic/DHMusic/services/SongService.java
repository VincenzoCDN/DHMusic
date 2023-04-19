package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.mapper.SongMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SongService {

    Logger logger = LoggerFactory.getLogger(SongService.class);

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
             logger.info("The song %d has been deleted", id);
             songRepository.delete(existSong);
         }else{
             logger.error("The song %d does not exist!", id);
             throw new RuntimeException("Song not exist!");
         }
    }
    //-----------------------------------------------------------------------
    public void deleteAll() {
        logger.info("All songs have been deleted.");
      songRepository.deleteAll();
        }
    //-----------------------------------------------------------------------
    public ResponseEntity<Song> addSong(SongDTO song) throws Exception {
        Song existSong = songRepository.findSongById(song.getId());
        if (existSong != null) {
            logger.error("The song you were looking for does not exist!");
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getIdArtistOfSong() == null) {
            logger.error("No requirements have been added");
            throw new IllegalArgumentException("Mistake! Required fields are missing.");
        }
        if (songRepository.findSongByTitle(song.getTitle()) != null){
            logger.error("The user entered an existing title.");
            throw new Exception("The title exist!");
        }
        if (artistRepository.findArtistById(song.getIdArtistOfSong()) == null){
            logger.error("The user has not entered an existing artist.");
            throw new Exception("Artist not exist!");
        }
        logger.info("The user has entered a new song.");
        return ResponseEntity.status(HttpStatus.CREATED).body(songRepository.save(songMapper.toSong(song)));
    }


    //-----------------------------------------------------------------------
        //aggiorna la canzone DTO
       public ResponseEntity<?> updateSong(Long id,SongDTO updateSong) {
            Song existSong = songRepository.findSongById(id); // id string o long?
            if (existSong == null) {
                logger.error("The song %d was not found", id);
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song %d not found");
            } else if (!existSong.getId().equals(updateSong.getId())) {
                logger.error("Song %d does not match", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song id does not match");
            } else if (updateSong.getTitle() != null) {
                logger.info("Changed only the title");
                existSong.setTitle(updateSong.getTitle());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's title has been successfully changed!") ;
            } else if (updateSong.getGenre() != null) {
                logger.info("Changed only the genre");
                existSong.setGenre(updateSong.getGenre());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's genre has been successfully changed!") ;
            } else if (updateSong.getIdArtistOfSong() != null) {
                logger.info("Changed only the artist");
                Artist artist = artistRepository.findArtistById(updateSong.getIdArtistOfSong());
                existSong.setArtistOfSong(artist);
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Artist's song has been successfully changed!") ;
            } else if (updateSong.getIdAlbumOfSong() != null) {
                logger.info("Changed only the album");
                Album album = albumRepository.findAlbumById(updateSong.getIdAlbumOfSong());
                existSong.setAlbumOfSong(album);
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's album has been successfully changed!") ;
            } else if (updateSong.getPublicationDate() != null || updateSong.getPublicationDate() == null) {
                logger.info("Changed only the publication date");
                existSong.setPublicationDate(updateSong.getPublicationDate());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's publication date has been successfully changed!") ;

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
            logger.info("The song %d has been successfully updated", id);
            return ResponseEntity.ok(existSong);
        }


    public ResponseEntity<Song> getSongById(Long id){
        Song existSong = songRepository.findSongById(id);
        if (existSong == null) {
            throw new RuntimeException("Song not exist!");
        }
        songRepository.findSongById(id);
        return ResponseEntity.ok().body(existSong);
    }






}





