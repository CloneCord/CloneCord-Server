package net.leloubil.clonecordserver.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * REST Controller for authentication-related endpoints
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    public static HashMap<String,AuthUser> tempStorage = new HashMap<>(); //TODO mongodb

    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public void signUp(@RequestBody AuthUser authData){
        authData.setPassword(passwordEncoder.encode(authData.getPassword()));
        //todo create real user from authdata
        tempStorage.put(authData.getUsername(),authData);
    }


}
