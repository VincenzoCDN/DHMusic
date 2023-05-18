package com.dhmusic.DHMusic.Components.entities.account.entities;

import com.dhmusic.DHMusic.Components.entities.content.entities.Album;
import com.dhmusic.DHMusic.Components.entities.content.entities.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.util.List;
@Entity
@Table(name = "artists")
public class Artist {
    /**
     * creazione classe Artist con le relative variabili, getter, setter e costruttori
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String artistName;
    private String bio;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("artist")
    private List<Album> albumsOfArtist;
    @OneToMany(mappedBy = "artistOfSong", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("artist")
    private List<Song> songOfArtist;
    private int follower;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    @JsonIgnoreProperties(value = "artist")
    private User user;
    @ManyToMany
    @JoinTable(name="followers", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="artist_id"))
    private List<User> usersFollowers;

    public Artist() {
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Album> getAlbumsOfArtist() {
        return albumsOfArtist;
    }

    public void setAlbumsOfArtist(List<Album> albumsOfArtist) {
        this.albumsOfArtist = albumsOfArtist;
    }

    public List<Song> getSongOfArtist() {
        return songOfArtist;
    }

    public void setSongOfArtist(List<Song> songOfArtist) {
        this.songOfArtist = songOfArtist;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsersFollowers() {
        return usersFollowers;
    }

    public void setUsersFollowers(List<User> usersFollowers) {
        this.usersFollowers = usersFollowers;
    }

    public int addFollower(){
        //this.usersFollowers.add(User);
        return this.follower++;
    }

    public int removeFollower(){
      //  this.usersFollowers.remove(User);
        return this.follower--;
    }
}
