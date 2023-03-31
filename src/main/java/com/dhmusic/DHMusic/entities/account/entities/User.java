package com.dhmusic.DHMusic.entities.account.entities;


import com.dhmusic.DHMusic.entities.content.entities.Playlist;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Artist artist;
    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Playlist> playlistOfUser;

    @Column(columnDefinition = "int default '0'",nullable = false)
    private int isAdmin; //chiedere per il boolean su db

    public User(){
        super();
    }
    public User(String username, String email, String password, String name, String surname, String dateOfBirth, String gender, String nationality) {
        super(username, email, password);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        this.setCreationDate(dtf.format(now));
        try {
            if (!isValidPassword(password)) {
                throw new AccountExceptions("Dati utente non validi");
            } else {
                this.setPassword(password);
            }
            if (!isValidEmail(email)) {
                throw new AccountExceptions("Dati utente non validi");
            } else {
                this.setEmail(email);
            }
        } catch (AccountExceptions e) {
            System.out.println("Email o password non valida");
        }
    }

    public boolean isValidPassword(String password){
        //controlla se è lunghezza almeno 8
        if(password.length()<8){
            return false;
        }

        //controlla la presenza di un simbolo speciale
        if(!password.matches(".*[!@#$%^&*()].*")){
            return false;
        }

        //controlla la presenza di una lettera maiuscola
        if(!password.matches(".*[A-Z].*")){
            return false;
        }
        //controlla la presenza di un numero
        if(!password.matches(".*\\d.*")){
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email){
        // Controlla la sintassi dell'email
        if (!email.matches("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}\\b")) {
            return false;
        }
        // Controlla la validità del dominio
        String[] parts = email.split("@");
        String domain = parts[1];
        try {
            InetAddress.getByName(domain);
        } catch (UnknownHostException ex) {
            return false;
        }
        // L'email è valida
        return true;
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
}


