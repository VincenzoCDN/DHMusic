package com.dhmusic.DHMusic.entities.account.entities;


import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.dhmusic.DHMusic.services.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Table(name = "users")
public class User extends Account {

    @Autowired
    private UserService userService;
    private String name;
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String nationality;

    @OneToMany(mappedBy = "usersFollowers")
    @JsonIgnore
    private List<Artist> followedArtists;
    private int isAdmin; //chiedere per il boolean su db
    boolean isAdmin1;

    public User(){

    }

    /*public User(){
        super();
    }

     */
    public User(String username, String email, String password, String name, String surname, String dateOfBirth, String gender, String nationality, int isAdmin) {
        super(username, email, password);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.setCreationDate(dtf.format(now));
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.isAdmin = isAdmin;
        try {
            if (!userService.isValidPassword(password)) {
                throw new AccountExceptions();
            } else {
                this.setPassword(password);
            }
            if (!userService.isValidEmail(email)) {
                throw new AccountExceptions();
            } else {
                this.setEmail(email);
            }
        } catch (AccountExceptions e) {
            System.out.println("Email o password non valida");
        }
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
    public boolean isAdmin1(){
        return isAdmin1;
    }
    public void setAdmin1(boolean isAdmin1){
        this.isAdmin1 = isAdmin1;
    }
}


