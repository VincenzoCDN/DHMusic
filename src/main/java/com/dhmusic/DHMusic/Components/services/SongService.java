package com.dhmusic.DHMusic.Components.services;

import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.dhmusic.DHMusic.Components.entities.content.entities.Album;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.dhmusic.DHMusic.Components.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.Components.mapper.SongMapper;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.Components.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.Components.repositories.content.repositories.SongRepository;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class SongService {

    /**
     * Inserito il logger in SongService per registrare informazioni utili
     * durante l'esecuzione del programma.
     */
    Logger logger = LoggerFactory.getLogger(SongService.class);

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongRepository songRepository; // per accesso al database
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;


    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per cancellare la singola canzone passando come
     * @param id
     * se la canzone esiste,la cancella, altrimenti darà un eccezione quali:
     * @throws Exception "La canzone non esiste".
     */

    public void deleteSong(Long id) throws Exception {
         Song existSong = songRepository.findSongById(id);
         if(existSong != null) {
             logger.info("The song %d has been deleted", id);
             songRepository.delete(existSong);
         }else{
             logger.error("The song %d does not exist!", id);
             throw new RuntimeException("Song not exist!");
         }
    }
    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per cancellare tutte le canzoni presenti.
     */
    public void deleteAll() {
        logger.info("All songs have been deleted.");
      songRepository.deleteAll();
        }
    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per aggiungere una canzone passando come parametro:
     * @param song utilizzando SongDTO
     * @return se i dati sono inseriti correttamente aggiunge e salva la canzone altrimenti può dare diverse eccezioni:
     * @throws Exception "la canzone già esiste"/"il titolo già esiste"/"l'artista non esiste".
     */
    public ResponseEntity<Song> addSong(SongDTO song) throws Exception {
        Song existSong = songRepository.findSongById(song.getId());
        if (existSong != null) {
            logger.error("The song you were looking for does not exist!");
            throw new RuntimeException("Song exist!");
        }
        if (song.getTitle() == null || song.getIdArtistOfSong() == null) {
            logger.error("No requirements have been added");
            throw new IllegalArgumentException("Mistake! Required fields are missing.");
        }
        if (songRepository.findSongByTitle(song.getTitle()) != null){
            logger.error("The user entered an existing title.");
            throw new Exception("The title exist!");
        }
        if (artistRepository.findArtistById(song.getIdArtistOfSong()) == null){
            logger.error("The user has not entered an existing artist.");
            throw new Exception("Artist not exist!");
        }
        logger.info("The user has entered a new song.");
        return ResponseEntity.status(HttpStatus.CREATED).body(songRepository.save(songMapper.toSong(song)));
    }


    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per aggiornare una canzone esistente
     * con la possibilità di aggiornare anche un singolo dato
     * i parametri utilizzati.
     *
     */
       public ResponseEntity<?> updateSong(Long id,SongDTO updateSong) {
            Song existSong = songRepository.findSongById(id); // id string o long?
            if (existSong == null) {
                logger.error("The song %d was not found", id);
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song %d not found");
            } else if (!existSong.getId().equals(updateSong.getId())) {
                logger.error("Song %d does not match", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song id does not match");
            } else if (updateSong.getTitle() != null) {
                logger.info("Changed only the title");
                existSong.setTitle(updateSong.getTitle());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's title has been successfully changed!") ;
            } else if (updateSong.getGenre() != null) {
                logger.info("Changed only the genre");
                existSong.setGenre(updateSong.getGenre());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's genre has been successfully changed!") ;
            } else if (updateSong.getIdArtistOfSong() != null) {
                logger.info("Changed only the artist");
                Artist artist = artistRepository.findArtistById(updateSong.getIdArtistOfSong());
                existSong.setArtistOfSong(artist);
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Artist's song has been successfully changed!") ;
            } else if (updateSong.getIdAlbumOfSong() != null ||updateSong.getIdAlbumOfSong() == null) {
                logger.info("Changed only the album");
                Album album = albumRepository.findAlbumById(updateSong.getIdAlbumOfSong());
                existSong.setAlbumOfSong(album);
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's album has been successfully changed!") ;
            } else if (updateSong.getPublicationDate() != null || updateSong.getPublicationDate() == null) {
                logger.info("Changed only the publication date");
                existSong.setPublicationDate(updateSong.getPublicationDate());
                songRepository.save(existSong);
                return ResponseEntity.ok().body("Song's publication date has been successfully changed!") ;

            } else {
                existSong.setTitle(updateSong.getTitle());
                existSong.setGenre(updateSong.getGenre());
                existSong.setLength(updateSong.getLength());
                Artist artist = artistRepository.findArtistById(updateSong.getIdArtistOfSong());
                existSong.setArtistOfSong(artist);
                Album album = albumRepository.findAlbumById(updateSong.getIdAlbumOfSong());
                existSong.setAlbumOfSong(album);
                songRepository.save(existSong);

            }
            logger.info("The song %d has been successfully updated", id);
            return ResponseEntity.ok(existSong);
        }

    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo che mostra i dati della singola canzone, utilizzando come parametro:
     * @param id
     * @return ritorna i dati della canzone se l'id è corretto,
     * altrimenti ritorna un eccezione nel caso la canzone non esista.
     */

    public ResponseEntity<Song> getSongById(Long id){
        Song existSong = songRepository.findSongById(id);
        if (existSong == null) {
            logger.error("Song not exist!");
            throw new RuntimeException("Song not exist!");
        }
        songRepository.findSongById(id);
        logger.info("the data of the song %d has been obtained", id);
        return ResponseEntity.ok().body(existSong);
    }
    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per visualizzare solo il titolo e il nome dell'artista
     * @param idSong
     * @return titolo + nome artista
     */


    public String getSongByIdWithArtist(Long idSong) {
        Song existSong = songRepository.findSongById(idSong);
        Artist artist = artistRepository.findArtistBySong(existSong);
        if (existSong == null) {
            logger.error("Song not exist!");
            throw new RuntimeException("Song not exist!");
        }
        logger.info("Song's title %d associated with the artist was obtained", idSong);
        return "Title: " + existSong.getTitle() + "\n" +
                "Artist: " + artist.getArtistName();
    }



    //------------------------------------------------------------------------------------------------------
    public String random3SongOfRandomArtist(){
        int totArtist= (int) artistRepository.count();
        Artist[] arrayOfArtist= artistRepository.findAll().toArray(new Artist[0]);

        Random random= new Random();
        int d = random.nextInt(totArtist);

        Artist pickArtist= arrayOfArtist[d];
        Long idArtistRandom= arrayOfArtist[d].getId();

        List<Song> listOfAristSong= songRepository.findSongsByArtistOfSong_id(idArtistRandom);
        long quantityOfSongArtist= listOfAristSong.size();
        Song[] arrayOfAristSong= listOfAristSong.toArray(new Song[0]);


        Set<Integer> randomIds = new HashSet<>();
        while (randomIds.size() < 3 ){
            int randomId= random.nextInt((int) (quantityOfSongArtist - 0));
            randomIds.add(randomId);
        }
        Integer[] idRandom= randomIds.toArray(new Integer[3]);

        Song song1= arrayOfAristSong[idRandom[0]];
        Song song2= arrayOfAristSong[idRandom[1]];
        Song song3= arrayOfAristSong[idRandom[2]];

        String str;
        str= pickArtist.getArtistName()+ "," + song1.getId().toString()+ "," +song2.getId().toString()+ "," +song3.getId().toString();

        return str;
    }


    public String getSongsRandomByGenre(){
        List<String> listGenre= songRepository.findDistinctGenres();
        int quantyOfgenre= listGenre.size();
        String[] arrayGenre= listGenre.toArray(new String[0]);

        Random random= new Random();
        int randomIdArray= random.nextInt((int) (quantyOfgenre - 0));

        String pickGenreRandom= arrayGenre[randomIdArray];

        List<Song> listOfAllTheSongWithGenre= songRepository.findSongByGenre(pickGenreRandom);
        int quantyOfSongWithGenre= listOfAllTheSongWithGenre.size();

        Song[] arraySongs = listOfAllTheSongWithGenre.toArray(new Song[0]);

        Set<Integer> randomIds = new HashSet<>();
        while (randomIds.size() < 3 ){
            int randomId= random.nextInt((int) (quantyOfSongWithGenre - 0));
            randomIds.add(randomId);
        }
        Integer[] idRandom= randomIds.toArray(new Integer[3]);

        Song song1= arraySongs[idRandom[0]];
        Song song2= arraySongs[idRandom[1]];
        Song song3= arraySongs[idRandom[2]];

        String str;
        str=   pickGenreRandom+ "," + song1.getId().toString() + "," + song2.getId().toString() + "," + song3.getId().toString();

        return str;
    }



    public String getLast3SongsCreate(){
        List<Song> listOfSongs= songRepository.findTop3ByOrderByCreationDatePlatformDesc();


        Song[] arrayOfSong= listOfSongs.toArray(new Song[0]);
        String str;

        str= arrayOfSong[0].getId().toString() + "," + arrayOfSong[1].getId().toString() + "," + arrayOfSong[2].getId().toString();

        return str;
    }

    public String playSong(Long id){
        Song song= songRepository.findSongById(id);
        String link= song.getLink();
        return link;
    }






}





