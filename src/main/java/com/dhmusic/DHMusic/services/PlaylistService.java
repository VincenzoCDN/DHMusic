package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.entities.content.entities.PlaylistDTO;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.mapper.PlaylistMapper;
import com.dhmusic.DHMusic.repositories.content.repositories.PlaylistRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private SongRepository songRepository;

    Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    public Playlist createPlaylist(PlaylistDTO playlistDTO){
        if(playlistDTO.getUserId() == null){
            logger.error("no userid was entered");
            throw new NullPointerException("userId is null ");
        }
        if(playlistDTO.getTitle() == null){
            logger.error("user "+playlistDTO.getUserId() + "did not enter the title in the playlist");
            throw new NullPointerException("title is null ");
        }
        Playlist playlist = playlistMapper.toPlaylist(playlistDTO);
        logger.info("creation new playlist with id " + playlist.getId());
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylist(){
        logger.info("the all playlist was shown");
        return playlistRepository.findAll();
    }

    public Playlist getById(Long playlistId){
        if(!playlistRepository.existsById(playlistId)){
            logger.error("not exist playlist with this id " +playlistId);
            throw new NullPointerException("there is no playlist with this id");
        }
        logger.info("the playlist with id "+playlistId+" was shown");
        return playlistRepository.findPlaylistById(playlistId);
    }

    public void deletePlaylist(Long playlistId) throws Exception {
        if(!playlistRepository.existsById(playlistId)){
            logger.error("not exist playlist with this id " +playlistId);
            throw new Exception("there is no playlist with this id");
        }
        playlistRepository.deleteById(playlistId);
    }

    public Playlist updatePlaylist(Long playlistId, Playlist playlist) throws Exception {
        Playlist existPlaylist = playlistRepository.findPlaylistById(playlistId);
         if(!playlistRepository.existsById(playlistId)){
             logger.error("not exist playlist with this id " +playlistId);
             throw new Exception("not exist Playlist");
         }
         if(playlist.getTitle()== null){
             logger.info("modified playlist");
            return playlistRepository.save(existPlaylist);
         }
         existPlaylist.setTitle(playlist.getTitle());
         logger.info("modified playlist");
         return playlistRepository.save(existPlaylist);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) throws Exception {
        Playlist existPlaylist = playlistRepository.findPlaylistById(playlistId);
        Song existSong = songRepository.findSongById(songId);
        if(existPlaylist == null){
            logger.error("not exist playlist with this id " +playlistId);
            throw new Exception("not exist Playlist");
        }
        else if(existSong == null){
            logger.error("not exist song with this id " +songId);
            throw new Exception("not exist Song");
        }
            existPlaylist.addSongToPlaylist(existSong);
            playlistRepository.save(existPlaylist);
            logger.info("add song with id " + songId + " into playlist with id " + playlistId);
    }

}
