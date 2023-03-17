package com.dhmusic.DHMusic.services;

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
        Song existSong = (Song) songRepository.findSongByTitle(song.getTitle()); //TODO
              //  && songRepository.findSongByArtist(song.getArtistOfSong());
        if (existSong != null){
            throw new RuntimeException("Song exist!");
        }
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
        Song existSong = (Song) songRepository.findSongById(song.getIdSong());
        if (existSong == null){
            throw new RuntimeException("Song not exist");
            //return null;
        }else{
            existSong.setTitle(song.getTitle());
            existSong.setArtistOfSong(song.getArtistOfSong());
        }
        return songRepository.save(existSong);
    }

}
