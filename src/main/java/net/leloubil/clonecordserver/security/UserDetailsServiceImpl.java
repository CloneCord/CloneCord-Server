package net.leloubil.clonecordserver.security;


import net.leloubil.clonecordserver.authentication.AuthController;
import net.leloubil.clonecordserver.authentication.AuthUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Class to get User details for spring security
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser u = AuthController.tempStorage.get(username);
        if (u  == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(u.getUsername(),u.getPassword(), Collections.emptyList());
    }
}
