package net.leloubil.clonecordserver.controllers;


import io.swagger.annotations.*;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.formdata.FormLogin;
import net.leloubil.clonecordserver.formdata.RegistrationUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related endpoints
 */

@RestController
@SuppressWarnings("deprecation")
@RequestMapping("/auth")
@Api( tags = "Authentication", description = "Log-in and sign-up")
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    @ApiOperation(value = "Create an account")
    public @ResponseBody User signUp(@Validated @RequestBody RegistrationUser registrationData){
        registrationData.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        return userService.createUser(registrationData);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Log in")
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
            ),
            @ApiResponse(code = 401, message = "Failed to log-in")
    })
    public void login(@Validated @RequestBody FormLogin loginData) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

}
