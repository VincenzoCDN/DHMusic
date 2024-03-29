package com.dhmusic.DHMusic.email;

import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.Components.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    UserService  userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;

    //TODO

    @PostMapping("/notification")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationDTO payload){
        try{
            User userToNotify = userRepository.getUserById(Long.valueOf(payload.getContactId()));
            System.out.println(" " );
            if (userToNotify == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the user");
            emailService.sendTo(userToNotify.getEmail(), payload.getTitle(), payload.getText());

            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }
}
