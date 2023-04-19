package com.dhmusic.DHMusic.entities.content.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class AlbumDTO {

    private String title;

    private Long artistID;

    private List<Long> musicID;

    private boolean isPublic;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date publicationDate;

    public AlbumDTO() {
    }

    public AlbumDTO(String title, Long artistID, List<Long> musicID, boolean isPublic, Date publicationDate) {
        this.title = title;
        this.artistID = artistID;
        this.musicID = musicID;
        this.isPublic = isPublic;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArtistID() {
        return artistID;
    }

    public void setArtistID(Long artistID) {
        this.artistID = artistID;
    }

    public List<Long> getMusicID() {
        return musicID;
    }

    public void setMusicID(List<Long> musicID) {
        this.musicID = musicID;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
