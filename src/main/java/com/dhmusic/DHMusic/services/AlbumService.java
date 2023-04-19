package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.AlbumDTO;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ArtistRepository artistRepository;


    public boolean isValidId(Long id) {
        boolean exists = albumRepository.existsById(id);
        if (exists == false) {
            return false;
        }
        return true;
    }

    //---------------------------------------------------------------------------------------
    //Metodi di Creazione:
    public void createAlbum(AlbumDTO albumDTO) throws Exception {
        if (albumDTO.getTitle() == null) {
            throw new Exception("the album need a Title");
        }
        if (albumDTO.getArtistID() == null) {
            throw new Exception("the album need a Artist");
        }
        if (albumDTO.getPublicationDate() == null) {
            throw new Exception("the album need Date with format (yyyy-MM-dd)");
        }


        if (!artistRepository.existsById(albumDTO.getArtistID())) {
            throw new Exception("Artist not found");
        }
        Album album = new Album();
        album.setTitle(albumDTO.getTitle());
        album.setPublicationDate(albumDTO.getPublicationDate());
        album.setPublic(albumDTO.isPublic());

        Artist existingArtist = artistRepository.findArtistById(albumDTO.getArtistID());
        album.setArtist(existingArtist);

        if (!(albumDTO.getMusicID() == null)) {
            albumRepository.save(album);

            Album album1 = albumRepository.findAlbumById(album.getId());

            addSongListInTheAlbum(albumDTO.getMusicID(), album.getId());
        }


        albumRepository.save(album);

    }


    //---------------------------------------------------------------------------------------
    //Metodi di UpDate:

    public Album updateTitile(Album album) throws Exception {
        Album newAlbum;
        newAlbum = (Album) albumRepository.findAllById(Collections.singleton(album.getId()));
        if (newAlbum == null) {
            throw new Exception("Album not found");
        }
        albumRepository.deleteById(album.getId());
        return albumRepository.save(newAlbum);
    }


    public Album updateArtist(Album album) throws Exception {


        Album newAlbum;
        newAlbum = (Album) albumRepository.findAllById(Collections.singleton(album.getId()));

        if (newAlbum == null) {
            throw new Exception("Album not found");
        }


        albumRepository.deleteById(album.getId());
        newAlbum = album;
        return albumRepository.save(newAlbum);
    }

    //---------------------------------------------------------------------------------------
    //Metodi per assegnare canzoni ad album:
    public String addSongsInTheAlbum(long idSong, long idAlbum) throws Exception {
        if (!albumRepository.existsById(idAlbum)) {
            throw new Exception("Album not found");
        }
        if (!songRepository.existsById(idSong)) {
            throw new Exception("Song not found");
        }
        Album existingAlbum = albumRepository.findAlbumById(idAlbum);
        Song existSong = songRepository.findSongById(idSong);

        existSong.setAlbumOfSong(existingAlbum);
        albumRepository.save(existingAlbum);
        return existSong.getTitle() + " is add in the Album " + existingAlbum.getTitle();

    }

    public String addSongListInTheAlbum(List<Long> idSongs, Long idAlbum) throws Exception {

        if (!albumRepository.existsById(idAlbum)) {
            throw new Exception("Album not found");
        }

        Song existSong = null;
        Album existingAlbum = null;


        for (Long idS : idSongs) {
            if (songRepository.findSongById(idS).getAlbumOfSong() != null) {
                throw new Exception("The song with id: " + idS + " has already assigned an album");

            } else if (songRepository.findSongById(idS).getAlbumOfSong() == null){
                existingAlbum = albumRepository.findAlbumById(idAlbum);
                if (existingAlbum.getSongsOfAlbum() == null) {
                    if (!songRepository.existsById(idS)) {
                        throw new Exception("Song not found");
                    }


                    existingAlbum = albumRepository.findAlbumById(idAlbum);
                    existSong = songRepository.findSongById(idS);

                    existSong.setAlbumOfSong(existingAlbum);
                    albumRepository.save(existingAlbum);
                }
            }
       }
        return existSong.getTitle() + " is add in the Album " + existingAlbum.getTitle();

    }




    //---------------------------------------------------------------------------------------
    //Metodi di Cancellazione:
    public void deleteAlbum(Long id){
        albumRepository.deleteById(id);
    }

    //---------------------------------------------------------------------------------------
    //Metodi per Cercare:
    public List<Album> getAllAlbum(){
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long id) throws Exception {
        if(!isValidId(id)){
            throw new Exception("The Album is not found!");
        }

        return albumRepository.findById(id);
    }
    //---------------------------------------------------------------------------------------
    //Metodi di Supporto per gli altri metodi:
    public List<Song> getSongByAlbumId(Long id)throws Exception {
        if (!isValidId(id)) {
            throw new Exception("The Album is not found!");
        }

        return songRepository.findSongByalbumOfSong(albumRepository.findAlbumById(id));
    }

    public List<Song> getSongByAlbumTitle(String title) throws Exception {
        Album albumNew= albumRepository.findByTitle(title);
        if (albumNew == null) {
            throw new Exception("The Title Album is not found!");
        }

         return  songRepository.findSongByalbumOfSong(albumRepository.findAlbumById(albumNew.getId()));
    }


}