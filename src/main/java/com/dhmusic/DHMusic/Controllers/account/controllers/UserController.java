package com.dhmusic.DHMusic.Controllers.account.controllers;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //inserisce un nuovo user nel database
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
            return userService.createUser(newUser);
    }

    //elimina un user nel database
    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response){
        userService.deleteSingleUser(id,response);
    }

    //Aggiorna un User nel database
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updateUser){
        return userService.updateUser(id, updateUser);
    }

    //Seleziona gli User nel database
    @GetMapping("/get-all-users")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    //Seleziona un User nel database
    @GetMapping("/get-user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //---------------------------------------------------------------------------------------
    //verifica il codice di Autenticazione

    @PutMapping("/verificate_code/{id}")
    public ResponseEntity<?> verificateUser(@PathVariable Long id, @RequestParam String code) {

        try {
            return ResponseEntity.accepted().body(userService.verificareAccount(id, code));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());

        }
    }
    //---------------------------------------------------------------------------------------
    //Aggiorna un normale Account ad un Artist
    @PutMapping("/UpdateAccountInToArtist/{id}")
    public ResponseEntity<?> updateAccountInToArtist(@PathVariable Long id){

        try {
            return ResponseEntity.accepted().body(userService.toAccountInArtist(id));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());

            }
    }

    //---------------------------------------------------------------------------------------
    //Ritorna lo status di un account
    @GetMapping("/statusAccount/{id}")
    public String statusAccount(@PathVariable Long id) {

        try {
            return userService.accoutStatus(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "Account not found";
        }
    }

    //---------------------------------------------------------------------------------------
    //Forgotten Password
    @GetMapping("/RescureForgottenPassword")
    public String forgottenPassword(@RequestBody String email){
    return userService.forgottenPassword(email);
    }











}