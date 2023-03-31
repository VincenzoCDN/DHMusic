package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlbumService{

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongRepository songRepository;






    public boolean isValidId(Long id)  {
        boolean exists = albumRepository.existsById(id);
        if (exists == false) {
            return false;
        }
        return true;
    }


    public void createAlbum(Album album) throws Exception {
               if (album.getTitle() == null) {
            throw new Exception("the album need a Title");
        }
       // if (album.getArtist() == null) {
     //       throw new Exception("the album need a Artist");
       // }
        //TODO
      // album.setPublicationDate();
        albumRepository.save(album);

    }


    public Album updateAlbum(Long id, Album album) throws Exception {

        if(!albumRepository.existsById(id)){
            throw new Exception("Album not found");
        }
        album.setId(id);
        return albumRepository.save(album);
    }

   /* public Album updateAlbum(Album album) throws Exception {
        Album newAlbum = (Album) albumRepository.findAllById(Collections.singleton(album.getId()));
        if(newAlbum == null){
            throw new Exception("Album not found");
        }
        newAlbum.setTitle(album.getTitle());

        return albumRepository.save(newAlbum);
    }*/


    public Album updateTitile(Album album) throws Exception {
        Album newAlbum;
        newAlbum = (Album) albumRepository.findAllById(Collections.singleton(album.getId()));
        if(newAlbum == null){
            throw new Exception("Album not found");
        }
        albumRepository.deleteById(album.getId());
        return albumRepository.save(newAlbum);
    }

    public Album updateArtist(Album album) throws Exception {


        Album newAlbum;
        newAlbum = (Album) albumRepository.findAllById(Collections.singleton(album.getId()));

        if(newAlbum == null){
            throw new Exception("Album not found");
        }


        albumRepository.deleteById(album.getId());
        newAlbum= album;
        return albumRepository.save(newAlbum);
    }


    public void deleteAlbum(Long id){
        albumRepository.deleteById(id);
    }


    public List<Album> getAllAlbum(){
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long id) throws Exception {
        if(!isValidId(id)){
            throw new Exception("The id is not found!");
        }
        return albumRepository.findById(id);
    }

    public String addSongsInTheAlbum(long idSong, long idAlbum) throws Exception {
        if(!albumRepository.existsById(idAlbum)){
            throw new Exception("Album not found");
        }
        if(!songRepository.existsById(idSong)){
            throw new Exception("Song not found");
        }
        Album existingAlbum= albumRepository.findAlbumById(idAlbum);
        Song existSong = songRepository.findSongById(idSong);

        existSong.setAlbumOfSong(existingAlbum);
        albumRepository.save(existingAlbum);
        return existSong.getTitle() + " is add in the Album " +  existingAlbum.getTitle();

    }


}