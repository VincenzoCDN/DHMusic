package com.dhmusic.DHMusic.Entities.content;


import static com.dhmusic.DHMusic.Entities.content.Album.playAlbum;
import static com.dhmusic.DHMusic.Entities.content.Playlist.playPlaylist;
import static com.dhmusic.DHMusic.Entities.content.Song.playSong;

public class Operation<T> {

    public void play(T item) {
        if (item instanceof Song) {
            playSong((Song) item);
        } else if (item instanceof Playlist) {
            playPlaylist((Playlist) item);
        } else if (item instanceof Album) {
            playAlbum((Album) item);
        } else {
            System.out.println("Item not supported");
        }
    }
}
