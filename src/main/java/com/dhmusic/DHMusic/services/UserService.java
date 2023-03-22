package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> userList = new ArrayList<>();
    public void createUser(User newUser) throws AccountExceptions {
        if (!isValidUser(newUser)) {
            throw new AccountExceptions("Dati utente non validi");
        }
        /*User user = new User(newUser.getUsername(),newUser.getEmail(),hashPassword(newUser.getPassword()),
                newUser.getName(),newUser.getSurname(),newUser.getDateOfBirth(),
                newUser.getGender(),newUser.getNationality());*/
        userList.add(newUser);
       // User savedUser = userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }
    public boolean isValidUser(User user){
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getSurname() == null || user.getSurname().isEmpty() ||
                user.getDateOfBirth() == null || user.getDateOfBirth().isEmpty() ||
                user.getGender() == null || user.getGender().isEmpty() ||
                user.getNationality() == null || user.getNationality().isEmpty()) {
            return false;
        }
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
