package com.dhmusic.DHMusic.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class UserService {

    public boolean isValidPassword(String password){
        //controlla se è lunghezza almeno 8
        if(password.length()<8){
            return false;
        }

        //controlla la presenza di un simbolo speciale
        if(!password.matches(".*[!@#$%^&*()].*")){
            return false;
        }

        //controlla la presenza di una lettera maiuscola
        if(!password.matches(".*[A-Z].*")){
            return false;
        }
        //controlla la presenza di un numero
        if(!password.matches(".*\\d.*")){
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email){
        // Controlla la sintassi dell'email
        if (!email.matches("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}\\b")) {
            return false;
        }
        // Controlla la validità del dominio
        String[] parts = email.split("@");
        String domain = parts[1];
        try {
            InetAddress.getByName(domain);
        } catch (UnknownHostException ex) {
            return false;
        }
        // L'email è valida
        return true;
    }

    public static String hashPassword(String password){
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password,salt);
    }

    public static boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password,hashedPassword);
    }
}
