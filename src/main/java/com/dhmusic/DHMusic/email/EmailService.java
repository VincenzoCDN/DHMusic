package com.dhmusic.DHMusic.email;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
 //   @Autowired
   // private UserService userService;

    @Value("${spring.mail.username}")
    String email1;

    public void sendTo(String email, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setFrom(email1);
        message.setText(text);
        javaMailSender.send(message);
    }


    public void sendCreateCode(String email, String code){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome in to DHMusic!");
        message.setFrom("dhmusicstreamingsong@gmail.com");
        message.setText("this is the code for verificate your account: \n" + code);
        javaMailSender.send(message);
    }

    public void sendForgottePW(String email, String code){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("DHMusi rescue team");
        message.setFrom("dhmusicstreamingsong@gmail.com");
        message.setText("this is recover code: \n" + code);
        javaMailSender.send(message);
    }


}
