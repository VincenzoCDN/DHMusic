package com.dhmusic.DHMusic.Components.entities.content.entities;




import com.dhmusic.DHMusic.Components.entities.account.entities.Artist;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.Date;
import java.util.List;
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name= "artist_id")
    private Artist artist;
    @OneToMany(mappedBy = "albumOfSong")
    @JsonIgnore
    private List<Song> songsOfAlbum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date publicationDate;

    private boolean isPublic;





    public Album(){

    }

    public Album(String title, Artist artist, List<Song> songsOfAlbum, Date publicationDate, boolean isPublic) {

        this.title = title;
        this.artist = artist;
        this.songsOfAlbum = songsOfAlbum;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Song> getSongsOfAlbum() {
        return songsOfAlbum;
    }

    public void setSongsOfAlbum(List<Song> songsOfAlbum) {
        this.songsOfAlbum = songsOfAlbum;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    protected static void playAlbum(Album album) {

        System.out.println("ciao");
    }


    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void addSong(Song song){
        this.songsOfAlbum.add(song);
    }


}
