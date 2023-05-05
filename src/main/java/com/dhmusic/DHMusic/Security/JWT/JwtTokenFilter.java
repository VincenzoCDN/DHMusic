package com.dhmusic.DHMusic.Security.JWT;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;


//@Component
public class JwtTokenFilter {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        // get authorization header and validate
//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header == null) {
//            chain.doFilter(request,response);
//            return;
//        }
//        // get JWT token and validate
//        final String token; //from header Bearer
//        try {
//            token = header.split(" ")[1].trim();
//        }catch (JWTVerificationException ex){
//            chain.doFilter(request,response);
//            return;
//        }
//
//        DecodedJWT decoded;
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(LoginService.JWT_SECRET)).withIssuer("develhope-demo").build();
//            decoded = verifier.verify(token);
//        }catch (JWTVerificationException ex){
//            chain.doFilter(request,response);
//            return;
//        }
//
//        // get user identity and set it on the spring security context
//        Optional<User> userDetails = Optional.ofNullable(userRepository.findUserById(decoded.getClaim("id").asLong()));
//        if(userDetails.isEmpty() || !userDetails.get().isActive()){
//            chain.doFilter(request,response);
//            return;
//        }
//
//        User user = userDetails.get();
//        //user.setPassword(null);
//        //TODO:user.setActivationCode(null);
//        //TODO:user.setPasswordResetCode(null);
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                user,null, getAuthorities(user)
//        );
//
//        authentication.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request)
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request,response);
//    }

}
