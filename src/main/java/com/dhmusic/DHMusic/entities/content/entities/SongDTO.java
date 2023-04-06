package com.dhmusic.DHMusic.entities.content.entities;

import com.dhmusic.DHMusic.entities.account.entities.Artist;

import java.util.Date;
import java.util.List;

public class SongDTO {

    private Long id;
    private String title;
    private float length;
    private Date publicationDate;
    private String genre;
    private boolean isPublic;
    private Artist artistOfSong;
    private Album albumOfSong;
    private List<Playlist> playlistOfSong;

    public SongDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Artist getArtistOfSong() {
        return artistOfSong;
    }

    public void setArtistOfSong(Artist artistOfSong) {
        this.artistOfSong = artistOfSong;
    }

    public Album getAlbumOfSong() {
        return albumOfSong;
    }

    public void setAlbumOfSong(Album albumOfSong) {
        this.albumOfSong = albumOfSong;
    }

    public List<Playlist> getPlaylistOfSong() {
        return playlistOfSong;
    }

    public void setPlaylistOfSong(List<Playlist> playlistOfSong) {
        this.playlistOfSong = playlistOfSong;
    }
}
