package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public Song addSong(Song song){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter song title:");
            String title = scanner.nextLine();

            System.out.println("Enter artist name:");
            String artist = scanner.nextLine();

            System.out.println("Enter album name:");
            String album = scanner.nextLine();

        Song existSong = (Song) songRepository.findSongByTitle(song.getTitle());
              //  && songRepository.findSongByArtist(song.getArtistOfSong());
        if (existSong != null){
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getArtistOfSong() == null ) {
            throw new IllegalArgumentException("Mistake! Required fields are missing");
        }
        Song newSong = new Song();

        return songRepository.save(song);
    }catch (Exception e){
            System.out.println("An error has occurred:"+ e.getMessage());
            return null;
        }finally {
            scanner.close();
        }
        }

    public void deleteSong (Song song){
        Song existSong = (Song) songRepository.findSongByTitle(song.getTitle()); //mettere indice
        if (existSong == null){
            throw new RuntimeException("Song not exist!");
        }
        //if() // TODO se la canzone è pubblica e l'utente è admin, cancella
        songRepository.delete(song);
    }

    public Song updateSong(Song song){
        Song existSong = (Song) songRepository.findSongById(song.getIdSong()); // id string o long?
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
