package com.dhmusic.DHMusic.entities.content.entities;



import com.dhmusic.DHMusic.entities.account.entities.User;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
