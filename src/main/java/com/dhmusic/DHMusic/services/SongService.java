package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository; // per accesso al database

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public Song addSong(@RequestBody Song song){

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

        }

    public void deleteSong (@RequestBody Song song) {
        User user = new User();
        try{
        Song existSong = (Song) songRepository.findSongByTitle(song.getTitle()); //mettere indice?
        if (existSong == null) {
            throw new RuntimeException("Song not exist!");
        }
        if (existSong.isPublic() && user.isAdmin1()
                || !existSong.isPublic() && !user.isAdmin1()
        || !existSong.isPublic()&& user.isAdmin1()) { // da vedere
            songRepository.delete(song);
        } else {
            throw new RuntimeException("You don't have permission to delete this song");
        }
    }catch (Exception e){
            System.out.println("An error has occurred:"+ e.getMessage());
        }
    }


    public Song updateSong(String id, @RequestBody Song song){
        Song existSong = (Song) songRepository.findSongById(song.getIdSong()); // id string o long?
        if (existSong == null){
            throw new RuntimeException("Song not exist");
        }else{
            existSong.setTitle(song.getTitle());
            existSong.setArtistOfSong(song.getArtistOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            existSong.setAlbumOfSong(song.getAlbumOfSong());
            //TODO GENERE
        }
        return songRepository.save(existSong);
    }

}
