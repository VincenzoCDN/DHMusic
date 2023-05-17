package com.dhmusic.DHMusic.Components.repositories.content.repositories;

import com.dhmusic.DHMusic.Components.entities.content.entities.Album;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    Song findSongByTitle(String title);
    List<Song> findSongByGenre(String genre); //TODO
    Song findSongById(Long id);
    List<Song> findSongByalbumOfSong(Album album);
    Song findSongByArtistOfSong(Long idArtist);
    // List<Song> findSongByArtistOfSong(Artist artist);


}

