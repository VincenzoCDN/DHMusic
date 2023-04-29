package com.dhmusic.DHMusic.Components.entities.content.entities;



import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonIgnore
    private User userId;
    @ManyToMany
    @JoinTable(name = "playlist_song",joinColumns =@JoinColumn(name = "song_id")
    ,inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    @JsonIgnore
    private List<Song> songsOfPlaylist;


    public Playlist() {

    }

    public Playlist(Long id, String title, User userId, List<Song> songsOfPlaylist) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.songsOfPlaylist = songsOfPlaylist;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<Song> getSongsOfPlaylist() {
        return songsOfPlaylist;
    }

    public void setSongsOfPlaylist(List<Song> songsOfPlaylist) {
        this.songsOfPlaylist = songsOfPlaylist;
    }

    public void addSongToPlaylist(Song song) {
        this.songsOfPlaylist.add(song);
    }
}
