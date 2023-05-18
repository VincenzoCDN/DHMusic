package com.dhmusic.DHMusic.entities.content.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SongDTO {

   private Long id;
   private String title;
   private float length;
   private String genre;
    private Long idArtistOfSong;
    private Long idAlbumOfSong;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isPublic;
    @JsonFormat(pattern = "yyyy/mm/dd")
    private Date publicationDate;
    private String fileSong;

    public SongDTO(){
    }

    public String getFileSong() {
        return fileSong;
    }

    public void setFileSong(String fileSong) {
        this.fileSong = fileSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdArtistOfSong() {
        return idArtistOfSong;
    }

    public void setIdArtistOfSong(Long idArtistOfSong) {
        this.idArtistOfSong = idArtistOfSong;
    }

    public Long getIdAlbumOfSong() {
        return idAlbumOfSong;
    }

    public void setIdAlbumOfSong(Long idAlbumOfSong) {
        this.idAlbumOfSong = idAlbumOfSong;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}