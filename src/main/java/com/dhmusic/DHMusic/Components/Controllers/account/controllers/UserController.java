package com.dhmusic.DHMusic.Components.Controllers.account.controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.LoginDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.services.LoginService;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    //inserisce un nuovo user nel database
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
            return userService.createUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(loginService.login(loginDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //elimina un user nel database
    @DeleteMapping("/delete-user/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response){
        userService.deleteSingleUser(id,response);
    }

    //Aggiorna un User nel database
    @PutMapping("/update-user/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id")
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
    @PostAuthorize("hasRole('ROLE_ADMIN') OR #id == authentication.principal.id")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/verificate_code/{id}")
    @PostAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> verificateUser(@PathVariable Long id, @RequestParam String code){
        try {
            return ResponseEntity.accepted().body(userService.verificareAccount(id, code));

    }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(e.getMessage());


    }
}
}