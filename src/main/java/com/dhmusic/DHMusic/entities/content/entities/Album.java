package com.dhmusic.DHMusic.entities.content.entities;




import com.dhmusic.DHMusic.entities.account.entities.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "albums")
public class Album implements Content{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idAlbum;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_names")
    private Artist artist;
    @OneToMany(mappedBy = "mAlbums")
    @JsonIgnore // a cosa serve? provate a toglierla e vedere cosa succede
    private List<Song> songsOfAlbum;
    private Date publicationDate;

    public Album(){

    }
    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Song> getSongsOfAlbum() {
        return songsOfAlbum;
    }

    public void setSongsOfAlbum(List<Song> songsOfAlbum) {
        this.songsOfAlbum = songsOfAlbum;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    protected static void playAlbum(Album album) {

        System.out.println("ciao");
    }
}
