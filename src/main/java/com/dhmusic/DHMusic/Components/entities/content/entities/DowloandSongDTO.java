package com.dhmusic.DHMusic.Components.entities.content.entities;


public class DowloandSongDTO {

    private Song song;
    private byte[] audioSong;

    public DowloandSongDTO() {
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public byte[] getAudioSong() {
        return audioSong;
    }

    public void setAudioSong(byte[] audioSong) {
        this.audioSong = audioSong;
    }
}
