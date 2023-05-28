package com.dhmusic.DHMusic.Components.repositories.account_repositories;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {

    Artist findByArtistName(String name);

    Artist findArtistById(Long id);

    Artist findByUserId(Long id);

    @Query("SELECT s.artistOfSong FROM Song s WHERE s = :song")
    Artist findArtistBySong(@Param("song") Song song);



}
