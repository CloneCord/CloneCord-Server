package net.leloubil.clonecordserver.authentication.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

import static net.leloubil.clonecordserver.security.SecurityConstants.TOKEN_PREFIX;
import static net.leloubil.clonecordserver.security.SecurityConstants.HEADER_STRING;
import static net.leloubil.clonecordserver.security.SecurityConstants.SECRET;

/**
 * Filter to ensure user is properly authenticated
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
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

        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX,""))
                .getSubject();

        if (user == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
    }
}
