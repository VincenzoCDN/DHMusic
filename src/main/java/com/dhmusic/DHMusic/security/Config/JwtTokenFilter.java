package com.dhmusic.DHMusic.security.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
import com.dhmusic.DHMusic.Components.repositories.account_repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    public final static String ENCRYPTION_KEY = UUID.randomUUID().toString();

    private Collection<? extends GrantedAuthority> getAuthorities(User user){
        return Arrays.asList(user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString())).toArray(SimpleGrantedAuthority[]::new));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        /*
        Preleva l'header "Authorization" da dentro la richiesta;
        preleva il token JWT dall'interno di questo header;
        lo decodifica e dentro ci legge chi è l'utente;
        lo cerca nel db, lo autentica e verifica se è autorizzato a fare le cose.
         */

        // preleviamo l'header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            filterChain.doFilter(request,response);
            return;
        }

         /*
        L'header Authorization creato da Postman è fatto così:
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IlJFR0lTVEVSRUQiLCJpc3MiOiJkZXZlbGhvcGUtZGVtbyIsImlkIjo2LCJleHAiOjE2ODM5MDMyMjgsImlhdCI6MTY4MjYwNzIyOH0.YUY3IiS4NTs70Wexup-2AgBzJf3mqU5Kmsl5R2Qpyp2JqYYC5VtaecExDNbAKknzHgKQu2OCvzWT2r7KRI-Avg
        La parola Bearer a noi non interessa, ci interessa solo il token, quindi facciamo split(" ")
        dividendo l'header in due pezzi ("Bearer" e "eyJ0eXAiO....") e poi prendiamo il secondo pezzo
        trim() toglie tutti gli spazi
         */
        final String token;
        try {
            token = header.split(" ")[1].trim();
        } catch (JWTVerificationException ex){
            filterChain.doFilter(request,response);
            return;
        }

        // decodifico il token
        DecodedJWT decodedJWT;
        try {
            JWTVerifier decoder = JWT.require(Algorithm.HMAC256(ENCRYPTION_KEY)).withIssuer("dhmusic").build();
            decodedJWT = decoder.verify(token);
        } catch (JWTVerificationException ex){
            filterChain.doFilter(request,response);
            return;
        }

        Optional<User> optional = userRepository.findById(decodedJWT.getClaim("id").asLong());
        if (optional.isEmpty()) {
            filterChain.doFilter(request,response);
            return;
        }

        User user = optional.get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,null, getAuthorities(user)
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
