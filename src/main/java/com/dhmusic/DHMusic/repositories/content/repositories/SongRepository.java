package com.dhmusic.DHMusic.repositories.content.repositories;

import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song,Integer> {
}
