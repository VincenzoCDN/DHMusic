package com.dhmusic.DHMusic.Security.JWT;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    // stringa SECRET(andrebbe nello YAML) mi permette si "firmare" il JWT
    public static final String JWT_SECRET = "b3f4db21-599c-41db-b531-77a951a3a67e";


}
