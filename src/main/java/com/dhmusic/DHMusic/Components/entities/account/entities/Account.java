package com.dhmusic.DHMusic.Components.entities.account.entities;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;
import java.util.List;
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
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private Date creationDate;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "DATETIME(0)")
    private Date updateDate;
    private boolean verificateEmail;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Roles.class)
    @Fetch(FetchMode.JOIN)
    private List<Roles> roles;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isVerificateEmail() {
        return verificateEmail;
    }

    public void setVerificateEmail(boolean verificateEmail) {
        this.verificateEmail = verificateEmail;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public void addRoles(Roles roles){
        this.roles.add(roles);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
