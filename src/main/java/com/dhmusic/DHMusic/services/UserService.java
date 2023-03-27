package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User newUser) throws AccountExceptions {
        if (!isValidUser(newUser)) {
            throw new AccountExceptions("Dati utente non validi");
        }
        String rawPsw = newUser.getPassword();
        String hashPsw = hashPassword(rawPsw);
        newUser.setPassword(hashPsw);
        User user = new User(
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getPassword(),
                newUser.getName(),
                newUser.getSurname(),
                newUser.getDateOfBirth(),
                newUser.getGender(),
                newUser.getNationality()
        );
        return userRepository.saveAndFlush(user);
    }

    public User getUserbyId(Long id){
        return userRepository.existsById(id)
                ? userRepository.getById(id)
                : new User();
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteSingleUser(Long id, HttpServletResponse response){
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else
            response.setStatus(409);
    }

    public User updateUser(Long id, String name){
        User user;
        if (userRepository.existsById(id)){
            user = userRepository.getById(id);
            user.setName(name);
            user = userRepository.saveAndFlush(user);
        }else{
            user = new User();
        }
        return user;
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
