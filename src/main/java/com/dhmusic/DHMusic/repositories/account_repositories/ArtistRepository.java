package com.dhmusic.DHMusic.repositories.account_repositories;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {

    Artist findByArtistName(String name);

    Artist findArtistById(Long id);

    Artist findByUserId(Long id);



}
