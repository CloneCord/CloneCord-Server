package net.leloubil.clonecordserver.security;


import net.leloubil.clonecordserver.authentication.LoginUser;
import net.leloubil.clonecordserver.services.LoginUserService;
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

    private final LoginUserService LoginUserService;

    public UserDetailsServiceImpl(LoginUserService LoginUserService) {
        this.LoginUserService = LoginUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginUser> u = LoginUserService.getLoginUserByEmail(username);
        if (u.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        LoginUser user = u.get();
        return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
