package net.leloubil.clonecordserver.controllers;


import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.formdata.FormLogin;
import net.leloubil.clonecordserver.formdata.RegistrationUser;
import net.leloubil.clonecordserver.data.User;
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

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public @ResponseBody User signUp(@Validated @RequestBody RegistrationUser authData){
        authData.setPassword(passwordEncoder.encode(authData.getPassword()));
        return userService.createUser(authData);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful login",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Authorization",
                                    description = "JWT Token for future api calls",
                                    response = String.class
                            )
                    }
            )
    })
    public void login(@Validated @RequestBody FormLogin user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

}
