package com.dhmusic.DHMusic.repositories.content.repositories;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository  extends JpaRepository<Album,Long> {




    public Album findAlbumByTitle (String title);

    public Album findAlbumById(long id);


    Album findByAlbumTitle(String title);
}
