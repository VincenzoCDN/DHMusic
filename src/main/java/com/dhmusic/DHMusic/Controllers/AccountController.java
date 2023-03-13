package com.dhmusic.DHMusic.Controllers;

import com.dhmusic.DHMusic.Entities.Account.Account;
import com.dhmusic.DHMusic.Entities.Account.User;
import com.dhmusic.DHMusic.Entities.content.Playlist;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController  {

    @GetMapping("creation-account")
    public User creationAccount(@RequestParam String usurname,@RequestParam String email,@RequestParam String name,
                                @RequestParam String surname,@RequestParam String dateOfBirth,@RequestParam String gender,
                                @RequestParam String nationality,@RequestParam int isAdmin){

        User account = new User(usurname,email,name,surname,dateOfBirth,gender,nationality,isAdmin);

        return account;
    }

    @GetMapping("creation-playlist")
    public Playlist creationPlaylist(@RequestParam Playlist title){
        return title;
    }

    @DeleteMapping("delete-playlist")
    public Playlist deletePlaylist(@RequestParam Playlist title){

        return title;

    }


}
