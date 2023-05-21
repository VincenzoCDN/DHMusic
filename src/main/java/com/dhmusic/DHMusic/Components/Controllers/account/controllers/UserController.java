package com.dhmusic.DHMusic.Components.Controllers.account.controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //elimina un user nel database
    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_REGISTERED')")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response){
        userService.deleteSingleUser(id,response);
    }

    //Aggiorna un User nel database
    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id AND hasRole('ROLE_ACTIVE')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updateUser){
        return userService.updateUser(id, updateUser);
    }

    //Seleziona gli User nel database
    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    //Seleziona un User nel database
    @GetMapping("/get-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/verificate_code/{id}")
    public ResponseEntity<?> verificateUser(@PathVariable Long id, @RequestParam String code){
        try {
            return ResponseEntity.accepted().body(userService.verificareAccount(id, code));

    }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());
    }
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-user-role/{id}")
    public String statusAccount(@PathVariable Long id){
        return userService.userStatus(id);

    }


    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-username/{id}")
    public String AccountUsername(@PathVariable Long id){
        return userService.accountUsername(id);
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/get-id")
    public String getUserId(@RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        String username= userService.decodeJWTForUsername(jwt);
        Long user= userService.foundUserIDByAccountName(username);



        return user.toString();
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @GetMapping("/autoban")
    public String autoBan(@RequestHeader("Authorization") String authorizationHeader) {

        String jwt = authorizationHeader.substring(7);
        String username= userService.decodeJWTForUsername(jwt);
        Long userid= userService.foundUserIDByAccountName(username);




        return  userService.banUser(userid);
    }




}