package com.dhmusic.DHMusic.Components.repositories.content.repositories;


import com.dhmusic.DHMusic.Components.entities.content.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    Playlist findPlaylistById(Long id);



}
