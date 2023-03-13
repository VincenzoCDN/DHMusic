package com.dhmusic.DHMusic.Controllers;


import com.dhmusic.DHMusic.Entities.Account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping (value = "/account")
    public  Account getAccount(){
        Account account = new Account("stellaromeo21","stellaromeo21@gmail.com", "hgufyfj");
            return account;
        };


    @PostMapping(value = "/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        System.out.println(account.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");

    }
}
