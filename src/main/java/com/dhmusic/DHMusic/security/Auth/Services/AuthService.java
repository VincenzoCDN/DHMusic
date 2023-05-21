package com.dhmusic.DHMusic.security.Auth.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dhmusic.DHMusic.security.Auth.Entities.LoginDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.security.Auth.Entities.Roles;
import com.dhmusic.DHMusic.security.Config.JwtTokenFilter;
import com.dhmusic.DHMusic.security.Config.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public String login(LoginDTO loginDTO) throws Exception {
        if(loginDTO == null) throw new Exception("Bad input");
        Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());
        if(optional.isEmpty()) throw new Exception("User not found");
        User user = optional.get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if(!passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return generateJWT(user);
    }

    public boolean canUserLogin(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    //https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static String getJWT(User user){
        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));
        String[] roles = user.getRoles().stream().map(Roles::toString).toArray(String[]::new);
        return JWT.create()
                .withIssuer("develhope-demo")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("username",user.getUsername())
                .withClaim("roles",String.join(",",roles))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JwtTokenFilter.ENCRYPTION_KEY));
    }

    public String generateJWT(User user) {
        String JWT = getJWT(user);
        System.out.println(JWT);
        return JWT;
    }


}
