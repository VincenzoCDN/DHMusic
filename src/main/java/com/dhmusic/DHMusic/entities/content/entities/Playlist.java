package com.dhmusic.DHMusic.entities.content.entities;



import com.dhmusic.DHMusic.entities.account.entities.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="playlists")
public class Playlist implements Content{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idPlaylist;
    private String title;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User creator;
    @ManyToMany
    @JoinTable(name = "playlist_song",joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private List<Song> songsOfPlaylist;
    private Date publicationDate;

    public Playlist() {

    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Song> getSongsOfPlaylist() {
        return songsOfPlaylist;
    }

    public void setSongsOfPlaylist(List<Song> songsOfPlaylist) {
        this.songsOfPlaylist = songsOfPlaylist;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    protected static void playPlaylist(Playlist playlist) {

    }
}
