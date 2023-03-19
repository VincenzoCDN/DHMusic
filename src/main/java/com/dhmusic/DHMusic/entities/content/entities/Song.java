package com.dhmusic.DHMusic.entities.content.entities;


import com.dhmusic.DHMusic.entities.account.entities.Artist;
import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Table(name="songs")
public class Song implements Content{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idSong;
    private String title;
    private float length;
    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name= "artist_id")
    private Artist artistOfSong;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album albumOfSong;

    @ManyToMany(mappedBy = "songsOfPlaylist")
    private List<Playlist> playlistOfSong;
    public Song(){

    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
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

    protected static void playSong(Song song) {
        System.out.println("we");
    }
}
