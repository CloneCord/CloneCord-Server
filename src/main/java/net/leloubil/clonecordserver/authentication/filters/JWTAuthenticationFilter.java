package net.leloubil.clonecordserver.authentication.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.leloubil.clonecordserver.formdata.LoginUser;
import net.leloubil.clonecordserver.services.LoginUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static net.leloubil.clonecordserver.security.SecurityConstants.*;

/**
 * Filter to create JWT on successfull login
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private LoginUserService loginUserService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, LoginUserService loginUserService) {
        this.authenticationManager = authenticationManager;
        this.loginUserService = loginUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            LoginUser credentials = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class); //read credentials

            //try authentication using spring auth module
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        //create JWT
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(loginUser.getUuid().toString())
                .withClaim("email",loginUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING,TOKEN_PREFIX + token);
    }
}
