package com.dhmusic.DHMusic.repositories.content.repositories;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    List<Song> findSongByTitle(String title);

    List<Song> findSongByArtistOfSong(Artist artist);

    List<Song> findSongByGenre(String genre); //TODO
    List<Song> findSongById(Long id);
    List<Song> findSongByAlbumOfSong(Album album);

}

