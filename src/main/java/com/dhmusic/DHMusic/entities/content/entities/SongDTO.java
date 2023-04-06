package com.dhmusic.DHMusic.entities.content.entities;


public class SongDTO {

   private String title;
   private String genre;
    private Long idArtistOfSong;
    private Long idAlbumOfSong;

    public SongDTO(){
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
