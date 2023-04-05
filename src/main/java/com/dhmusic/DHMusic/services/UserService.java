package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.mapper.UserMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<?> createUser(UserDTO newUser) {
        if (!isValidUser(newUser) || !isValidEmail(newUser.getEmail()) || !isValidPassword(newUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
        User user = userMapper.toArtist(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    public ResponseEntity<?> getUserById(Long id){
        return userRepository.existsById(id)
                ? ResponseEntity.status(HttpStatus.OK).body(userRepository.getById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
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

    public ResponseEntity<?> updateUser(Long id, UserDTO updateUser){
        User user;
        if (userRepository.existsById(id)){
            user = userRepository.getById(id);
            user.setName(updateUser.getName() != null ? updateUser.getName() : user.getName());
            user.setSurname(updateUser.getSurname() != null ? updateUser.getSurname() : user.getSurname());
            user.setUsername(updateUser.getUsername() != null ? updateUser.getUsername() : user.getUsername());
            user.setEmail(updateUser.getEmail() != null ? updateUser.getEmail() : user.getEmail());
            user.setPassword(updateUser.getPassword() != null ? updateUser.getPassword() : user.getPassword());
            user.setDateOfBirth(updateUser.getDateOfBirth() != null ? updateUser.getDateOfBirth() : user.getDateOfBirth());
            user.setGender(updateUser.getGender() != null ? updateUser.getGender() : user.getGender());
            user.setNationality(updateUser.getNationality() != null ? updateUser.getNationality() : user.getNationality());
            user = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    public boolean isValidUser(UserDTO user){
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getSurname() == null || user.getSurname().isEmpty()) {
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

    public boolean isValidPassword(String password){
        if(password.length()<8){
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

}
