package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.stereotype.Service;

//@Service
public class SongService {

    public Song addSong(String title, String artist, String genre, float duration){
        Song newSong = new Song();
        return newSong;
    }

}
