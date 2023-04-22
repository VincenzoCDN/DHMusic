package com.dhmusic.DHMusic.mapper;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.entities.content.entities.PlaylistDTO;
import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.repositories.content.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {



    @Autowired
    UserRepository userRepository;

    public Playlist toPlaylist(PlaylistDTO playlistDTO){
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDTO.getTitle());
        User user =  userRepository.findUserById(playlistDTO.getId());
        playlist.setUserId(user);
        return playlist;
    }

}
