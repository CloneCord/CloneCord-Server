package net.leloubil.clonecordserver.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.formdata.FormLogin;
import net.leloubil.clonecordserver.formdata.RegistrationUser;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related endpoints
 */

@RestController
@RequestMapping("/auth")
@SecurityRequirements()
@Tag(name = "Authentication", description = "Log-in and sign-up")
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    @Operation(summary = "Create an account")
    public @ResponseBody User signUp(@Validated @RequestBody RegistrationUser registrationData){
        registrationData.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        return userService.createUser(registrationData);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful login",
                    headers = {
                            @Header(
                                    name = "Authorization",
                                    description = "JWT Token for future api calls"
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Failed to log-in")
    })
    public void login(@Validated @RequestBody FormLogin loginData) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

}
