package net.leloubil.clonecordserver.security;


import net.leloubil.clonecordserver.authentication.AuthUser;
import net.leloubil.clonecordserver.services.AuthUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Class to get User details for spring security
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserService authUserService;

    public UserDetailsServiceImpl(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> u = authUserService.getAuthUserByEmail(username);
        if (u.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        AuthUser user = u.get();
        return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
