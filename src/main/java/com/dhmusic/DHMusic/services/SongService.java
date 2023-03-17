package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public Song addSong(Song song){
        if (song.getTitle() == null || song.getArtistOfSong() == null ) {
            throw new IllegalArgumentException("Mistake! Required fields are missing");
        }
        return songRepository.save(song);
    }

    public void deleteSong (Song song){
        // bisogna mettere eccezione se utente è admin?
        songRepository.delete(song);
    }

    public Song updateSong(Song song){
        Song existSong = (Song) songRepository.findSongById(song.getIdSong()); //TODO ragionare
        if (existSong == null){
            return null;
        }else{
            existSong.setTitle(song.getTitle());
            existSong.setArtistOfSong(song.getArtistOfSong());
        }
        return songRepository.save(existSong);
    }

}
