package com.dhmusic.DHMusic.Entities.Account;
import com.dhmusic.DHMusic.Entities.Content.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account implements AccountOperation,AccountObject {

    private String id;
    protected String username;
    protected String password;
    protected String email;

    private List<Playlist>playlists;
    public boolean isPlaying;
    public boolean isStopping;
    public boolean isSkipping;
    public boolean isPausing;
    public boolean isRepeating;
    public boolean isShuffling;
    public boolean isAdding;
    public boolean isRemoving;
    public boolean isShuffle;
    public boolean isLiking;
    public boolean isDisliking;
    public boolean isSearching;
    public boolean hasRightsToAddSong;
    public boolean hasRightsToAddPlaylist;

    public Account(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = UUID.randomUUID().toString();
        this.playlists = new ArrayList<>();
        this.isPlaying = true;
        this.isStopping = true;
        this.isSkipping = true;
        this.isPausing = true;
        this.isRepeating = true;
        this.hasRightsToAddSong = true;
        this.hasRightsToAddPlaylist = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public List<Playlist> getPlaylists() {

        return playlists;
    }

    @Override
    public void createAccount() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void updateAccount() {

    }
}
