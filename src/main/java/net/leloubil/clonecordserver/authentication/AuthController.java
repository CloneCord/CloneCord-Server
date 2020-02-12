package net.leloubil.clonecordserver.authentication;


import net.leloubil.clonecordserver.services.AuthUserService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related endpoints
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService authUserService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthUserService authUserService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authUserService = authUserService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public void signUp(@RequestBody AuthUser authData){
        authData.setPassword(passwordEncoder.encode(authData.getPassword()));
        userService.createUser(authData);
    }


}
