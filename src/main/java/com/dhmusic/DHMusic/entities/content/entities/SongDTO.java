package com.dhmusic.DHMusic.entities.content.entities;


public class SongDTO {

   private String title;
    private Long idArtistOfSong;
    private Long idAlbumOfSong;

    public SongDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
