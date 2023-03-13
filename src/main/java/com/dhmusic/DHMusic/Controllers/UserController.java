package com.dhmusic.DHMusic.Controllers;

import com.dhmusic.DHMusic.Entities.Account.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    //inserisce un nuovo user nel database
    @PostMapping("/create-user")
    public User createUser(@RequestBody User newUser){
        //logica di creazione dell'User
        return newUser;
    }

    //elimina un user nel database
    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestBody User newUser){
        //logica di rimozione dell'User
    }

    //Aggiorna un User nel database
    @PutMapping("/update-User")
    public User updateUser(@RequestBody User newUser){
        //logica di aggiornamento dell'User (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-User-name), (/update-User-surname)
        return newUser;
    }

    //Seleziona gli User nel database
    @GetMapping("/get-all-Users")
    public User getAllUser(){
        //logica di restituzione degli User
        return null;
    }

    //Seleziona un User nel database
    @GetMapping("/get-User-by-id")
    public User getUserById(){
        //logica di restituzione dell User tramite id
        return null;
    }

}