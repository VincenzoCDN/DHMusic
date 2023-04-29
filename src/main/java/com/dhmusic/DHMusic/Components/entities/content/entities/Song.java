package com.dhmusic.DHMusic.Components.entities.content.entities;


import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Date;
import java.util.List;

@Entity
@Table(name="songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private float length;

    @JsonFormat(pattern = "yyyy/mm/dd")
    @Column(columnDefinition = "DATE")
    private Date publicationDate;

    private String genre;
    @Column(length = 100)
    private String link;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy/mm/dd")
    @Column(columnDefinition = "DATE")
    private Date creationDatePlatform;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name= "artist_id", nullable = false)
    @JsonIgnore
    private Artist artistOfSong;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonIgnore
    private Album albumOfSong;

    @ManyToMany(mappedBy = "songsOfPlaylist")
    private List<Playlist> playlistOfSong;
    public Song(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Playlist> getPlaylistOfSong() {
        return playlistOfSong;
    }

    public void setPlaylistOfSong(List<Playlist> playlistOfSong) {
        this.playlistOfSong = playlistOfSong;
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

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getCreationDatePlatform() {
        return creationDatePlatform;
    }

    public void setCreationDatePlatform(Date creationDatePlatform) {
        this.creationDatePlatform = creationDatePlatform;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
