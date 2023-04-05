package com.dhmusic.DHMusic.entities.account.entities;
import jakarta.persistence.*;


import java.util.Random;
import java.util.Scanner;

@MappedSuperclass
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 20)
    private String username;
    @Column(nullable = false, length=200)
    private String password;
    @Column(unique = true, length = 45)
    private String email;

    private String creationDate;

    private boolean verificateEmail;

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isVerificateEmail() {
        return verificateEmail;
    }

    public void setVerificateEmail(boolean verificateEmail) {
        this.verificateEmail = verificateEmail;
    }


}
