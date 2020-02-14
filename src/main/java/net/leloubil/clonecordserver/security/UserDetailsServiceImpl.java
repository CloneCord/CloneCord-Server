package net.leloubil.clonecordserver.security;


import net.leloubil.clonecordserver.formdata.LoginUser;
import net.leloubil.clonecordserver.services.LoginUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginUser> u = LoginUserService.getLoginUserByEmail(username);
        if (u.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return u.get();
    }

}
