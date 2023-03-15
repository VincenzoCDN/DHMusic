package com.dhmusic.DHMusic.entities.content.entities;


import static com.dhmusic.DHMusic.entities.content.entities.Song.playSong;

public class Operation<T> {

    public void play(T item) {
        if (item instanceof Song) {
            playSong((Song) item);
        } else if (item instanceof Playlist) {
            Playlist.playPlaylist((Playlist) item);
        } else if (item instanceof Album) {
            Album.playAlbum((Album) item);
        } else {
            System.out.println("Item not supported");
        }
    }
}
