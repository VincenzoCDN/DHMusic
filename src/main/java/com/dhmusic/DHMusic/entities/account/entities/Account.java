package com.dhmusic.DHMusic.entities.account.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Random;
import java.util.Scanner;

@Entity
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    private String password;
    private String email;
    private String creationDate;
    private boolean loggedIn; //verificare se utile o meno
    private boolean verificateEmail;
    private String codeVerificate;

    public Account() {

    }
    public Account(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isVerificateEmail() {
        return verificateEmail;
    }

    public void setVerificateEmail(boolean verificateEmail) {
        this.verificateEmail = verificateEmail;
    }

    public String getCodeVerificate() {
        return codeVerificate;
    }

    public void setCodeVerificate(String codeVerificate) {
        this.codeVerificate = codeVerificate;
    }

    public void setEmail(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(6);
        Random random= new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        String code= sb.toString();
        this.email= email;
        this.verificateEmail= false;
        this.codeVerificate= code;
        //send email with code.
    }

    public void verificateEmail(){
        Scanner scanner = new Scanner(System.in);
        String code= scanner.nextLine();
        if (code.equals(codeVerificate)){
            this.verificateEmail= true;
            System.out.println("The email is \"Verificate\"");
        } else {
            System.out.println("The code is not correct");
        }

    }

}
