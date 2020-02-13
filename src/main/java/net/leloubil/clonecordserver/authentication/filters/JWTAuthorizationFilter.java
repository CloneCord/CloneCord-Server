package net.leloubil.clonecordserver.authentication.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.services.LoginUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static net.leloubil.clonecordserver.security.SecurityConstants.TOKEN_PREFIX;
import static net.leloubil.clonecordserver.security.SecurityConstants.HEADER_STRING;
import static net.leloubil.clonecordserver.security.SecurityConstants.SECRET;

/**
 * Filter to ensure user is properly authenticated
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final LoginUserService loginUserService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, LoginUserService loginUserService) {
        super(authenticationManager);
        this.loginUserService = loginUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request,response); //do nothing
            return;
        }

        UsernamePasswordAuthenticationToken authToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authToken); //add user auth to security context
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HEADER_STRING);

        if (token == null){
            return null;
        }

        String userId = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX,""))
                .getSubject();

        if (userId == null){
            return null;
        }
        Optional<LoginUser> user = loginUserService.getLoginUserById(UUID.fromString(userId));
        return new UsernamePasswordAuthenticationToken(user.orElse(null),null,new ArrayList<>());
    }
}
