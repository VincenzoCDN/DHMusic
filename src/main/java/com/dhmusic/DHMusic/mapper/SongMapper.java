package com.dhmusic.DHMusic.mapper;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongMapper {
    @Autowired
     ArtistRepository artistRepository;
    @Autowired
    AlbumRepository albumRepository;

    public SongMapper(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    public Song toSong(SongDTO songDTO){
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setGenre(songDTO.getGenre());
        song.setLength(songDTO.getLength());
        song.setPublic(songDTO.isPublic());
        Optional<Artist> artist = artistRepository.findById(songDTO.getIdArtistOfSong());//Optional yes or not?
        song.setArtistOfSong(artist);
        Optional<Album> album = albumRepository.findById(songDTO.getIdAlbumOfSong());
        song.setAlbumOfSong(album);
        return song;
    }




}
