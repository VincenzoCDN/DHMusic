package com.dhmusic.DHMusic.repositories.content.repositories;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    Playlist findPlaylistById(Long id);

}
