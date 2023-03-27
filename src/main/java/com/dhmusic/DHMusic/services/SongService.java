package com.dhmusic.DHMusic.services;

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
        return songRepository.save(song);
    }
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
        }
        return songRepository.save(existSong);
    }
    //---------------------------------------------------------------------------------------

    public void deleteSong(Long id) {                              //OK
         Song existSong = songRepository.findSongById(id);
         if(existSong != null) {
             songRepository.delete(existSong);
         }else{
             throw new RuntimeException("Song not exist!");
         }

    }





}