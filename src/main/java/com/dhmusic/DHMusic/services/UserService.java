package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.email.EmailService;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.mapper.UserMapper;
import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> createUser(UserDTO newUser) {
        if (!isValidUser(newUser) || !isValidEmail(newUser.getEmail()) || !isValidPassword(newUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid data");
        }
        try {
            User user = userMapper.toUser(newUser);
            user.setVerificationCode(generateCode());
            emailService.sendCreateCode(user.getEmail(), user.getVerificationCode());
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("account already exist");
        }
    }

    public ResponseEntity<?> getUserById(Long id){
        return userRepository.existsById(id)
                ? ResponseEntity.status(HttpStatus.OK).body(userRepository.findUserById(id))
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
            user = userRepository.findUserById(id);
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

    public String generateCode(){
        String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        String[] alphaArray= alphaNumericStr.split("");
        List<String> tempList= new ArrayList<>();
        for (int i=0; i <= 6; i++){
            Random random= new Random();
            int n = random.nextInt(alphaNumericStr.length()-1);
            String s = alphaArray[n];
            tempList.add(s);
        }
        String code= tempList.toString().replaceAll(",","").replaceAll("[^\\w\\s]", "").replaceAll(" ", "");

        return code;

    }
    //---------------------------------------------------------------------------------------
    //Metodi di controllo Email:
    public String verificareAccount(long id, String code){
        if(!userRepository.existsById(id)){
            return "Account not found";
        }


        User existingUser= userRepository.findUserById(id);

        if (existingUser.isVerificateEmail()){
            return "The mail is already authenticated";
        }

      if(Objects.equals(existingUser.getVerificationCode(), code)) {
          existingUser.setVerificateEmail(true);
          userRepository.save(existingUser);
          return "The code is correct. \nYour Account is validate now!";

      } else {
          return "The code is not correct.\nPlease check end try again.";
      }



    }

}
