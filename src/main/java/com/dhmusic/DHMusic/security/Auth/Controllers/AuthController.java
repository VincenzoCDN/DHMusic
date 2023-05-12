package com.dhmusic.DHMusic.security.Auth.Controllers;

import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.Components.services.UserService;
import com.dhmusic.DHMusic.security.Auth.Entities.LoginDTO;
import com.dhmusic.DHMusic.security.Auth.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(authService.login(loginDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
        return userService.createUser(newUser);
    }
}
