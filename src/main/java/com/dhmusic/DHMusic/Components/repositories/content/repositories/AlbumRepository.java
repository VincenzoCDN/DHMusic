package com.dhmusic.DHMusic.Components.repositories.content.repositories;

import com.dhmusic.DHMusic.Components.entities.content.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlbumRepository  extends JpaRepository<Album,Long> {

    Album findAlbumById(Long id);

    Album findByTitle(String title);

}
