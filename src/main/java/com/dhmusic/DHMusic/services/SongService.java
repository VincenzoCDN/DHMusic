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
        if (song.getTitle() == null || song.getArtistOfSong() == null ) {
            throw new IllegalArgumentException("The title and the artisgitt are mandatory fields to insert!");
        }
        return songRepository.save(song);
    }

}
