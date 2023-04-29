package com.dhmusic.DHMusic.Components.mapper;

import com.dhmusic.DHMusic.Components.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.Components.entities.content.entities.PlaylistRTO;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PlaylistMapperRTO {

    Logger logger = LoggerFactory.getLogger(PlaylistMapperRTO.class);
    public PlaylistRTO toPlaylistRTO(Playlist playlist){
        PlaylistRTO playlistRTO = new PlaylistRTO();
        playlistRTO.setTitle(playlist.getTitle());

        List<Song> songList = playlist.getSongsOfPlaylist();
        List<String> songTitle = new ArrayList<>();
        for (int i = 0; i < songList.size(); i++) {

            songTitle.add(playlist.getSongsOfPlaylist().get(i).getTitle());

        }
        playlistRTO.setSongTitle(songTitle);
        return playlistRTO;
    }



    public List<PlaylistRTO> toAllPlaylistRTO(List<Playlist> playlistList) {

        logger.info("entro in to all playlistRTO");

        List<PlaylistRTO> playlistRTO = new ArrayList<>();

        logger.info("creo un nuovo  RTO list");

        for (int j = 0; j < playlistList.size(); j++) {

            logger.info("entro nel for");

            List<Song> songList = playlistList.get(j).getSongsOfPlaylist();

            logger.info("setto una lista di canzoni");

            List<String> songTitle = new ArrayList<>();

            logger.info("setto una lista di nomi di canzoni");
            for (int i = 0; i < songList.size(); i++) {

                logger.info("entro nel for 2 ");

                songTitle.add(songList.get(i).getTitle());

                logger.info("aggiungo i nomi nella lista di nomi di canzoni");
            }

            logger.info("esco da for 2");

            playlistRTO.add(new PlaylistRTO(playlistList.get(j).getTitle(),songTitle));

            logger.info("aggiungo nuovi RTO nella lista di RTO ");

        }
        logger.info("esco da for ");
        return playlistRTO;
    }

}
