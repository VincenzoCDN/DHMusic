package com.dhmusic.DHMusic.Components.mapper;


import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.Components.entities.content.entities.PlaylistDTO;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {



    @Autowired
    UserRepository userRepository;

    public Playlist toPlaylist(PlaylistDTO playlistDTO){
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDTO.getTitle());
        User user =  userRepository.findUserById(playlistDTO.getUserId());
        playlist.setUserId(user);
        return playlist;
    }

}
