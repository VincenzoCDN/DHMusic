package com.dhmusic.DHMusic.services;


import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService{

    @Autowired
    private AlbumRepository albumRepository;






    public boolean isValidId(Long id)  {
        boolean exists = albumRepository.existsById(id);
        if (exists == false) {
            return false;
        }
        return true;
    }


    public void createAlbum(Album album) throws Exception {
        if (album == null) {
            throw new Exception("the album is empty");
        }
        albumRepository.save(album);

    }


    public Album updateAlbum(Long id, Album album) throws Exception {

        if(!albumRepository.existsById(id)){
            throw new Exception("Album not found");
        }
        album.setId(id);
        return albumRepository.save(album);
    }

      public Album updateAlbum(Album album) throws Exception {
        Album newAlbum = albumRepository.findAlbumByTitle(album.getTitle());
        if(newAlbum == null){
            throw new Exception("Album not found");
        }
          newAlbum.setTitle(album.getTitle());

            return albumRepository.save(newAlbum);
    }

    public void deleteAlbum(Long id){
        albumRepository.deleteById(id);
    }

    public String changePublic(Album album)  {
       Album newAlbum = albumRepository.findAlbumById(album.getId());
         if (newAlbum.isPublic() == false) {
             newAlbum.setPublic(true);
            return "The Album is now public!";
        } else {
             newAlbum.setPublic(false);
             return "The Album is not public!";
        }


    }

    /*
    public String addArtist(Album album){

        Album newAlbum = albumRepository.findAlbumById(album.getId());

    }



   public String editArtist(Album album)
    Album newAlbum = albumRepository.findAlbumById(album.getId());

    }
    */

    public List<Album> getAllAlbum(){
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long id) throws Exception {
        if(!isValidId(id)){
            throw new Exception("The id is not found!");
        }
        return albumRepository.findById(id);
    }

}
