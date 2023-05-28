package com.dhmusic.DHMusic.security.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dhmusic.DHMusic.Components.entities.account.entities.User;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

public class JwtTokenFilter extends OncePerRequestFilter {


    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;

    public final static String ENCRYPTION_KEY = "cavallo";

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Arrays.asList(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).toArray(SimpleGrantedAuthority[]::new));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        /*if (header == null) {
            response.sendError(400, "Missing authorization header 2");
            filterChain.doFilter(request, response);
            return;
        }*/
        if(header!=null) {
            final String token;
            try {
                token = header.split(" ")[1].trim();
                System.out.println(token);
            } catch (JWTVerificationException ex) {
                response.sendError(400, "Cannot read token properly");
                filterChain.doFilter(request, response);
                return;
            }
            // decodifico il token
            DecodedJWT decodedJWT;
            try {
                //JWTVerifier decoder = JWT.require(Algorithm.HMAC512(ENCRYPTION_KEY)).withIssuer("dhmusic").build();
                //decodedJWT = decoder.verify(token);
                decodedJWT = JWT.decode(token);
            } catch (JWTVerificationException ex) {
                response.sendError(400, "Cannot decode JWT");
                filterChain.doFilter(request, response);
                return;
            }
        /*if (user == null) {
            response.sendError(400, "User not found");
            filterChain.doFilter(request,response);
            return;
        }*/
            String username = decodedJWT.getClaim("username").asString();
            UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

