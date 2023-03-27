package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database

    //---------------------------------------------------------------------------------------
    public Song addSong(@RequestBody Song song) {

        Song existSong = songRepository.findSongByTitle(song.getTitle()).get(0);
        //  && songRepository.findSongByArtist(song.getArtistOfSong());
        if (existSong != null) {
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getArtistOfSong() == null) {
            throw new IllegalArgumentException("Mistake! Required fields are missing");
        }
        Song newSong = new Song();
        return songRepository.save(song);

    }
    //---------------------------------------------------------------------------------------
    public void deleteSong(@RequestBody Song song) {
        User user = new User();
        try {
            Song existSong = songRepository.findSongByTitle(song.getTitle()).get(0); //mettere indice?
            if (existSong == null) {
                throw new RuntimeException("Song not exist!");
            }
//            if (existSong.isPublic() && user.isAdmin1()
//                    || !existSong.isPublic() && !user.isAdmin1()
//                    || !existSong.isPublic() && user.isAdmin1()) { // da vedere
//                songRepository.delete(song);
//            } else {
//                throw new RuntimeException("You don't have permission to delete this song");
//            }
        } catch (Exception e) {
            System.out.println("An error has occurred:" + e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------
    public Song updateSong(String id, @RequestBody Song song) {
        Song existSong = songRepository.findSongById(song.getId()).get(0); // id string o long?
        if (existSong == null) {
            throw new RuntimeException("Song not exist");
        } else {
            existSong.setTitle(song.getTitle());
            existSong.setArtistOfSong(song.getArtistOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            //TODO genre
        }
        return songRepository.save(existSong);
    }
    //---------------------------------------------------------------------------------------
    public List<Song> searchSong(Song song) {
        String title = song.getTitle();
        Album album = song.getAlbumOfSong();
        Artist artist = song.getArtistOfSong();
        Long id = song.getId();
        //TODO genre
        if (title == null && artist == null) {  //da ragionare
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
        return null; // da vedere

    }
}