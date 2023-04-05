package com.dhmusic.DHMusic.services;

import com.dhmusic.DHMusic.email.EmailService;
import com.dhmusic.DHMusic.entities.account.entities.User;
import com.dhmusic.DHMusic.entities.content.entities.Album;
import com.dhmusic.DHMusic.entities.content.entities.Song;
import com.dhmusic.DHMusic.entities.exception.AccountExceptions;
import com.dhmusic.DHMusic.repositories.account_repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.cache.spi.support.CollectionReadOnlyAccess;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User createUser(User newUser) throws AccountExceptions {
        if (!isValidUser(newUser)) {
            throw new AccountExceptions("Dati utente non validi");
        }
        String rawPsw = newUser.getPassword();
        String hashPsw = hashPassword(rawPsw);
        newUser.setPassword(hashPsw);

        newUser.setVerificationCode(generateCode());

        User user = new User(
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getPassword(),
                newUser.getName(),
                newUser.getSurname(),
                newUser.getDateOfBirth(),
                newUser.getGender(),
                newUser.getNationality(),
                newUser.getVerificationCode()
        );

            userRepository.saveAndFlush(user);

            User tempUser= userRepository.findByEmail(newUser.getEmail());

            emailService.sendCreateCode(tempUser.getEmail(), tempUser.getVerificationCode());


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

    public String verificareAccount(long id, String code){
        if(!userRepository.existsById(id)){
            return "Account not found";
        }


        User existingUser= userRepository.findUserById(id);

        if (existingUser.isVerificateEmail()== true){
            return "The mail is already authenticated";
        }

      if(Objects.equals(existingUser.getVerificationCode(), code)) {
          existingUser.setVerificateEmail(true);
          userRepository.save(existingUser);
          return "The code is correct. \nYour Account is validate now!";

      } else {
          return "the code is not correct.\nPlease check end try again.";
      }



    }
}
