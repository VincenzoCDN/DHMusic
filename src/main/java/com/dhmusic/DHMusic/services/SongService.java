package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.content.entities.SongDTO;
import com.dhmusic.DHMusic.mapper.SongMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.ArtistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.AlbumRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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

    @Autowired
    private FileStorageService fileStorageService;


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
    public Song addSong(SongDTO song) throws Exception {
        // TODO non si inserisce l'id nel dto... al massimo possiamo cercare omonimie
        //  però le omonimie sono previste
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
            throw new Exception("Song not exist!");
        }
        logger.info("The user has entered a new song.");
        Song savedSong = songRepository.save(songMapper.toSong(song));
        // Chiama il metodo uploadFileSong per caricare il file e associarlo al brano salvato

        return savedSong;
    }

    public Song addFileToSong(long songId, MultipartFile file) throws Exception {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if(optionalSong.isEmpty()) throw new Exception("song not found");
        Song song = optionalSong.get();
        // fileStorageService.upload() assigns the file a name, save it into the hard disk and return the name
        String fileName = fileStorageService.upload(file);
        song.setFileSong(fileName);
        return songRepository.save(song);
    }


    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo per aggiornare una canzone esistente
     * con la possibilità di aggiornare anche un singolo dato
     * i paramentri utilizzati.
     */
       public Object updateSong(Long id, SongDTO updateSong) {
            Song existSong = songRepository.findSongById(id); // id string o long?
            if (existSong == null) {
                logger.error("The song %d was not found", id);
               return "Song %d not found";
            } else if (!existSong.getId().equals(updateSong.getId())) {
                logger.error("Song %d does not match", id);
                return "Song id does not match";
            } else if (updateSong.getTitle() != null) {
                logger.info("Changed only the title");
                existSong.setTitle(updateSong.getTitle());
                songRepository.save(existSong);
                return "Song's title has been successfully changed!";
            } else if (updateSong.getGenre() != null) {
                logger.info("Changed only the genre");
                existSong.setGenre(updateSong.getGenre());
                songRepository.save(existSong);
                return "Song's genre has been successfully changed!";
            } else if (updateSong.getIdArtistOfSong() != null) {
                logger.info("Changed only the artist");
                Artist artist = artistRepository.findArtistById(updateSong.getIdArtistOfSong());
                existSong.setArtistOfSong(artist);
                songRepository.save(existSong);
                return "Song's song has been successfully changed!";
            } else if (updateSong.getIdAlbumOfSong() != null) {
                logger.info("Changed only the album");
                Album album = albumRepository.findAlbumById(updateSong.getIdAlbumOfSong());
                existSong.setAlbumOfSong(album);
                songRepository.save(existSong);
                return "Song's album has been successfully changed!";
            } else if (updateSong.getPublicationDate() != null || updateSong.getPublicationDate() == null) {
                logger.info("Changed only the publication date");
                existSong.setPublicationDate(updateSong.getPublicationDate());
                songRepository.save(existSong);
                return "Song's publication date has been successfully changed!";

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
            return existSong;
        }

    //------------------------------------------------------------------------------------------------------

    /**
     * Creazione metodo che mostra i dati della singola canzone, utilizzando come parametro:
     * @param id
     * @return ritorna i dati della canzone se l'id è corretto,
     * altrimenti ritorna un eccezione nel caso la canzone non esista.
     */

    public Song getSongById(Long id){
        Song existSong = songRepository.findSongById(id);
        if (existSong == null) {
            logger.error("Song not exist!");
            throw new RuntimeException("Song not exist!");
        }
        songRepository.findSongById(id);
        logger.info("the data of the song %d has been obtained", id);
        return existSong;
    }
    //------------------------------------------------------------------------------------

    /**
     * Questo metodo recupera il file audio di una canzone dal servizio di archiviazione file, dato l'ID della canzone.
     * Innanzitutto controlla se la canzone esiste nel repository e recupera il nome del file.
     * Quindi, chiama il servizio di archiviazione file per scaricare il file audio e restituirlo come matrice di byte.
     * @param songId l'ID del brano il cui file audio deve essere recuperato.
     * @return un array di byte contenente il file audio della canzone
     * @throws Exception se l'ID del brano non è valido o non è stato possibile recuperare il file
     * dal servizio di archiviazione file
     */




    public byte[] getFileSong(Long songId) throws Exception {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if(optionalSong.isEmpty()) throw new Exception("Cannot find song " + songId);
        String fileName = optionalSong.get().getFileSong();
        return fileStorageService.download(fileName);
    }






}





