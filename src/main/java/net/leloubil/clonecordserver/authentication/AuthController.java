package net.leloubil.clonecordserver.authentication;


import net.leloubil.clonecordserver.services.LoginUserService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related endpoints
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(LoginUserService LoginUserService, UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public void signUp(@Validated @RequestBody RegistrationUser authData){
        authData.setPassword(passwordEncoder.encode(authData.getPassword()));
        userService.createUser(authData);
    }


}
