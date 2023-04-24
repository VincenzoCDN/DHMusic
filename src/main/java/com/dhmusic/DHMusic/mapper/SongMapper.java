package com.dhmusic.DHMusic.mapper;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongMapper {
    @Autowired
     ArtistRepository artistRepository;
    @Autowired
    AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public SongMapper(ArtistRepository artistRepository,
                      SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    /**
     * Il metodo toSong all'interno della classe SongMapper serve per convertire un oggetto SongDTO in un oggetto Song.
     * Questa conversione è necessaria quando si vuole salvare una nuova canzone nel database,
     * perché in genere si riceve un oggetto DTO dalla richiesta HTTP e si deve convertire in un oggetto persistente.
     * @param songDTO
     * @return song
     */

    public Song toSong(SongDTO songDTO){
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setGenre(songDTO.getGenre());
        song.setLength(songDTO.getLength());
        song.setIsPublic(songDTO.isPublic());
        song.setLink(songDTO.getLink());
        song.setPublicationDate(songDTO.getPublicationDate());
        Artist artist = artistRepository.findArtistById(songDTO.getIdArtistOfSong());
        song.setArtistOfSong(artist);
        if (songDTO.getIdAlbumOfSong() != null) {
            Album album = albumRepository.findAlbumById(songDTO.getIdAlbumOfSong());
            song.setAlbumOfSong(album);
            }
        return song;
    }





}
