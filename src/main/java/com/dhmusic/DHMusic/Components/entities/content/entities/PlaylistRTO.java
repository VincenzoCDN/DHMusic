package com.dhmusic.DHMusic.Components.entities.content.entities;

import java.util.List;

public class PlaylistRTO {

    private String title;

    private List<String> songTitle;

    public PlaylistRTO() {
    }

    public PlaylistRTO(String title, List<String> songTitle) {
        this.title = title;
        this.songTitle = songTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(List<String> songTitle) {
        this.songTitle = songTitle;
    }
}
