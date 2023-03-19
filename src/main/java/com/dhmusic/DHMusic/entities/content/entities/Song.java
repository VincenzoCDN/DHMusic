package com.dhmusic.DHMusic.entities.content.entities;


import com.dhmusic.DHMusic.entities.account.entities.Artist;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "songs")
public class Song implements Content{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSong;
    private String title;
    private float length;
    private Date publicationDate;
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "artist_songs")
    private Artist artistOfSong;
    @ManyToOne
    @JoinColumn(name = "album_songs")
    private Album albumOfSong;

    public Song(){

    }

    public Long getIdSong() {
        return idSong;
    }

    public void setIdSong(Long idSong, boolean isPublic) {
        this.idSong = idSong;
        this.isPublic = isPublic; //???

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

    public boolean isPublic(){
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    static void playSong(Song item){
        System.out.println("we");
    }

}
