package com.dhmusic.DHMusic.Components.entities.account.entities;


public class ArtistDTO {

    private String artistName;

    private String bio;

    private Long userId;

    public ArtistDTO() {
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
