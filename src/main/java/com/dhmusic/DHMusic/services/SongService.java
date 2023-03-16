package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public Song addSong(String title, Artist artist, String genre, float duration){ // discutere genere
        Song newSong = new Song();
        newSong.setTitle(title);
        newSong.setArtistOfSong(artist);
        newSong.setLength(duration);
        return newSong;
    }

}
