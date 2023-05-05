package com.dhmusic.DHMusic.Components.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dhmusic.DHMusic.Components.entities.account.entities.LoginDTO;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import com.dhmusic.DHMusic.security.Config.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginService {

    // stringa SECRET(andrebbe nello YAML) mi permette si "firmare" il JWT

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public String login(LoginDTO loginDTO){
        if(loginDTO == null) return null;
        Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());
        if(optional.isEmpty()) return null;

        if (optional.get().getPassword() != loginDTO.getPassword()) return null;

        return generateJWT(optional.get());
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
        String[] roles = user.getRoles().stream().map(role -> role.toString()).toArray(String[]::new);
        return JWT.create()
                .withIssuer("develhope-demo")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("roles",String.join(",",roles)) //funziona su nuove versioni Java (17)
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JwtTokenFilter.ENCRYPTION_KEY));
    }

    public String generateJWT(User user) {
        String JWT = getJWT(user);

        // user.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(user);

        return JWT;
    }
}
