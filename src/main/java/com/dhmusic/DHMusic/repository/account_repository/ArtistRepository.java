package com.dhmusic.DHMusic.repository.account_repository;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {

    public Artist findByArtistName(String name);

}
