package com.dhmusic.DHMusic.repositories.content.repositories;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Integer> {

    List<Song> findSongByTitle(String title);

    List<Song> findSongByArtist(String artist);

    List<Song> findSongByGenre(String genre);
    List<Song> findSongById(String id);
}
