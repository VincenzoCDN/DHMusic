package com.dhmusic.DHMusic.Components.services;

import com.dhmusic.DHMusic.email.EmailService;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.entities.account.entities.UserDTO;
import com.dhmusic.DHMusic.Components.mapper.UserMapper;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    private String errorMessage = "";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseEntity<?> createUser(UserDTO newUser) {
        if (!isValidUser(newUser) || !isValidEmail(newUser.getEmail()) || !isValidPassword(newUser.getPassword())) {
            logger.error("createUser: " + errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        User user = userMapper.toUser(newUser);
        try {
            user.setVerificationCode(generateCode());
            emailService.sendCreateCode(user.getEmail(), user.getVerificationCode());
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
        }catch(DataIntegrityViolationException ex){
            String response = "";
            if(userRepository.existsUserByUsernameIgnoreCase(user.getUsername())) response ="Username already token";
            if(userRepository.existsUserByEmail(user.getEmail())) response ="Account already exist";
            logger.error("createUser:"+response);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<?> getUserById(Long id){
        return userRepository.existsById(id)
                ? ResponseEntity.status(HttpStatus.OK).body(userRepository.getUserById(id))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    public User getById(Long id){
        return userRepository.existsById(id)
                ? userRepository.getUserById(id)
                : null;
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
            user = userRepository.getUserById(id);
            if(updateUser.getEmail() != null){
                if(isValidEmail(updateUser.getEmail())) {
                    user.setEmail(updateUser.getEmail());
                    user.setVerificationCode(generateCode());
                    emailService.sendCreateCode(user.getEmail(), user.getVerificationCode());
                }else {
                    logger.error("UpdateUser:"+errorMessage);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
                }
            }
            if(updateUser.getPassword() != null) {
                if (isValidPassword(updateUser.getPassword())) {
                    user.setPassword(encoder.encode(updateUser.getPassword()));
                }else {
                    logger.error("UpdateUser:"+errorMessage);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
                }
            }
            user.setName(updateUser.getName() != null ? updateUser.getName() : user.getName());
            user.setSurname(updateUser.getSurname() != null ? updateUser.getSurname() : user.getSurname());
            user.setUsername(updateUser.getUsername() != null ? updateUser.getUsername() : user.getUsername());
            user.setDateOfBirth(updateUser.getDateOfBirth() != null ? updateUser.getDateOfBirth() : user.getDateOfBirth());
            user.setGender(updateUser.getGender() != null ? updateUser.getGender() : user.getGender());
            user.setNationality(updateUser.getNationality() != null ? updateUser.getNationality() : user.getNationality());
            try {
                user = userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }catch(DataIntegrityViolationException ex){
                String response = "";
                if(userRepository.existsUserByUsernameIgnoreCase(user.getUsername())) response ="Username already token";
                if(userRepository.existsUserByEmail(user.getEmail())) response ="Account already exist";
                logger.error("UpdateUser:"+response);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }else{
            errorMessage="...User not found...";
            logger.error("UpdateUser:"+errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    public boolean isValidUser(UserDTO user){
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getSurname() == null || user.getSurname().isEmpty()) {
            errorMessage="...Not all user data has been entered...";
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password){
        if(password.length()<8){
            errorMessage="...Password length min 8 has been not met...";
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email){
        // Controlla la sintassi dell'email
        if (!email.matches("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}\\b")) {
            errorMessage="...Incorrect email syntax...";
            return false;
        }
        // Controlla la validità del dominio
        String[] parts = email.split("@");
        String domain = parts[1];
        try {
            InetAddress.getByName(domain);
        } catch (UnknownHostException ex) {
            errorMessage="...Wrong email domain...";
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
        String code = tempList.toString()
                      .replaceAll(",","")
                      .replaceAll("[^\\w\\s]", "")
                      .replaceAll(" ", "");
        logger.info("the verification code has been generated");
        return code;

    }
    //---------------------------------------------------------------------------------------
    //Metodi di controllo Email:                                                             Modificato, controlalre
    public String verificareAccount(long id, String code){
        if(!userRepository.existsById(id)){
            return "Account not found";
        }
        User existingUser = userRepository.findUserById(id);

        if (existingUser.isVerificateEmail()){
            return "The mail is already authenticated";
        }

        if(Objects.equals(existingUser.getVerificationCode(), code)) {
            existingUser.setVerificateEmail(true);
            existingUser.setRoles(Collections.singletonList(Roles.ROLE_ACTIVE));
            existingUser.setVerificationCode(null);
            userRepository.save(existingUser);
            logger.info("The account " + existingUser.getId() +" has been authenticated");
            return "The code is correct. \nYour Account is validate now!";

        } else {
            return "The code is not correct.\nPlease check end try again.";
        }
    }
    //---------------------------------------------------------------------------------------
    //Ritorna lo status dell'account TODO
    public String accoutStatus(Long id) {
        User existingUser = userRepository.findUserById(id);
        List<String> role= Collections.singletonList(existingUser.getRoles().toString());
        String status = null;

        if (role.contains(Roles.ROLE_BANNED)) {
          status = "Banned";
        }
        else if (role.contains(Roles.ROLE_REGISTERED)) {
            status = "not Active";
        }
        else if (role.contains(Roles.ROLE_ACTIVE)) {
           return  "Active";
        }
       else if (role.contains(Roles.ROLE_ARTIST)) {
            return  "Artist";
        }
        else if (role.contains(Roles.ROLE_ADMIN)) {
            return  "Admin";

        }


        return status;
    }

    //---------------------------------------------------------------------------------------
    //forgotten Password
    public String forgottenPassword(String email) {
        if (!userRepository.existsUserByEmail(email)) {
            return "Account not found";
        }

        User existingUser = userRepository.findUserByEmail(email);
        existingUser.setVerificationCode(generateCode());

        emailService.sendForgottePW(existingUser.getEmail(), existingUser.getVerificationCode());
        return "The code is send in your emailBox.";

    }

    //---------------------------------------------------------------------------------------
    //Create admin
    public String createAdmin(){
        if (!userRepository.existsUserByEmail("dhmusicstreamingsong@gmail.com")){
            User user = new User();
            user.setRoles(Collections.singletonList(Roles.ROLE_ADMIN));
            user.setUsername("dhmusic");
            user.setPassword(encoder.encode("admin"));
            user.setEmail("dhmusicstreamingsong@gmail.com");
            user.setName("admin");
            user.setSurname("admin");

            userRepository.save(user);
            return "account admin create";
        } else {
            return "account not create";
        }
    }



}
