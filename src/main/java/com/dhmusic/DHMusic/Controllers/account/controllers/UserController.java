package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.dhmusic.DHMusic.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //inserisce un nuovo user nel database
    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody User newUser) {
        try {
           userService.createUser(newUser);
            return ResponseEntity.ok(newUser);
        }catch(AccountExceptions e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
        }
    }

    //elimina un user nel database
    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response){
        userService.deleteSingleUser(id,response);
    }

    //Aggiorna un User nel database
    @PutMapping("/update-user/{id}")
    public User updateUser(@PathVariable Long id, @RequestParam String name){
        return userService.updateUser(id, name);
    }

    //Seleziona gli User nel database
    @GetMapping("/get-all-users")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    //Seleziona un User nel database
    @GetMapping("/get-user/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserbyId(id);
    }

    @PutMapping("/verificate_code/{id}")
    public ResponseEntity verificateUser(@PathVariable Long id, @RequestParam String code){
        try {
        userService.verificareAccount(id, code);

        return ResponseEntity.accepted().body("The code is correct. \nYour Account is validate now!");

    }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());


    }
}
}