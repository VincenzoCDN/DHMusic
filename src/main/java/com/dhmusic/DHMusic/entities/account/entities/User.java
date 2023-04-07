package com.dhmusic.DHMusic.entities.account.entities;


import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Account {
    @Column(nullable = false,length = 45)
    private String name;
    @Column(nullable = false,length = 45)
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    @ManyToMany(mappedBy = "usersFollowers")
    private List<Artist> followedArtists;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private Artist artist;
    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Playlist> playlistOfUser;

    @Column(columnDefinition = "int default '0'",nullable = false)
    private int isAdmin; //chiedere per il boolean su db

    private String verificationCode;

    public User(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }


    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void setFollowedArtists(List<Artist> followedArtists) {
        this.followedArtists = followedArtists;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Playlist> getPlaylistOfUser() {
        return playlistOfUser;
    }

    public void setPlaylistOfUser(List<Playlist> playlistOfUser) {
        this.playlistOfUser = playlistOfUser;
    }


    //---------------------

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    //--------------------
}


