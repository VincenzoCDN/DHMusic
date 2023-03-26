package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database


    //---------------------------------------------------------------------------------------
    public Song addSong(Song song) {

        /*Song existSong = songRepository.findSongByTitle(song.getTitle()).get(1);
        //  && songRepository.findSongByArtist(song.getArtistOfSong());
        if (existSong != null) {
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getArtistOfSong() == null) {
            throw new IllegalArgumentException("Mistake! Required fields are missing");
        }
*/
        return songRepository.save(song);

    }
    //---------------------------------------------------------------------------------------
    /*public void deleteSong(@RequestBody Song song) {
        User user = new User();
        try {
            Song existSong = songRepository.findSongByTitle(song.getTitle()); //mettere indice?
            if (existSong == null) {
                throw new RuntimeException("Song not exist!");
            }
            if (existSong.isPublic() && user.isAdmin1()
                    || !existSong.isPublic() && !user.isAdmin1()
                    || !existSong.isPublic() && user.isAdmin1()) { // da vedere
                songRepository.delete(song);
            } else {
                throw new RuntimeException("You don't have permission to delete this song");
            }
        } catch (Exception e) {
            System.out.println("An error has occurred:" + e.getMessage());
        }
    }

     */


    //---------------------------------------------------------------------------------------
    public Song updateSong(Song song) {
        Song existSong = songRepository.findSongById(song.getId()); // id string o long?
        if (existSong == null) {
            throw new RuntimeException("Song not exist");
        } else {
            existSong.setTitle(song.getTitle());
            existSong.setArtistOfSong(song.getArtistOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            existSong.setGenre(song.getGenre());
            return songRepository.save(existSong);
        }
    }
    //---------------------------------------------------------------------------------------
    public Song searchSong(Song song) {
        String title = song.getTitle();
        Album album = song.getAlbumOfSong();
        Artist artist = song.getArtistOfSong();
        Long id = song.getId();
        String genre = song.getGenre();
        /*if (title == null && artist == null) {  //da ragionare
            throw new IllegalArgumentException("At least one search parameter is required");
        }
        if (title != null) {
            return songRepository.findSongByTitle(title);
        }
        if (album != null) {
            return songRepository.findSongByAlbumOfSong(album);
        }
//        if (artist != null) {
//            return songRepository.findSongByArtist(artist);
//        }
        if (id != null) {
            return songRepository.findSongById(id);
            }
       */

        return songRepository.findSongByTitle(title); // da vedere

    }
    //-----------------------------------------------------------------------
    public void deleteSong(Long id) {                              //OK
         Song existSong = songRepository.findSongById(id);
         if(existSong != null) {
             songRepository.delete(existSong);
         }else{
             throw new RuntimeException("Song not exist!");
         }

    }





}