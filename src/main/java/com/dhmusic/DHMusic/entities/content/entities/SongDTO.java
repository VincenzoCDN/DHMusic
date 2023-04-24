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

    private String link;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isPublic;
    @JsonFormat(pattern = "yyyy/mm/dd")
    private Date publicationDate;

    public SongDTO(){
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
    public String getLink() {
        return link;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
