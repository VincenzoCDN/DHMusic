package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.dhmusic.DHMusic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //inserisce un nuovo user nel database
    @PostMapping("/create-user")
    public void createUser(@RequestBody User newUser) {
        try {

            userService.createUser(newUser);
        }catch(AccountExceptions e){
            System.out.println(e.getMessage());
        }

    }

    //elimina un user nel database
    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestBody User newUser){
        //logica di rimozione dell'User
    }

    //Aggiorna un User nel database
    @PutMapping("/update-user")
    public User updateUser(@RequestBody User newUser){
        //logica di aggiornamento dell'User (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-User-name), (/update-User-surname)
        return newUser;
    }

    //Seleziona gli User nel database
    @GetMapping("/get-all-users")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    //Seleziona un User nel database
    @GetMapping("/get-user-by-id")
    public User getUserById(){
        //logica di restituzione dell User tramite id
        return null;
    }

}