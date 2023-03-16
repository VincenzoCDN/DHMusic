package com.dhmusic.DHMusic.entities.account.entities;



import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;



import java.util.List;

public class Artist extends User {
    private String bio;

    private List<Album> albumsOfArtist;

    private List<Song> songOfArtist;
    private int follower;

    private List<User> usersFollowers;

    public Artist(String username, String email, String password, String name, String surname, String dateOfBirth, String gender, String nationality, String bio, int isAdmin) {
        super(username, email, password, name, surname, dateOfBirth, gender, nationality);
        this.bio = bio;
    }

    public String getBio(){
        return this.bio;
    }
    public void setBio(String bio){
        this.bio = bio;
    }

    public int addFollower(){
        //this.usersFollowers.add(User);
        return this.follower++;
    }

    public int removeFollower(){
      //  this.usersFollowers.remove(User);
        return this.follower--;
    }
}
