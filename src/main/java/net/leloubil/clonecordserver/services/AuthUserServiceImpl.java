package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.authentication.AuthUser;
import net.leloubil.clonecordserver.persistence.AuthUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


    @Override
    public AuthUser createAuthUser(AuthUser authUser) {
        return authUserRepository.insert(authUser);
    }

    @Override
    public Optional<AuthUser> getAuthUserByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    @Override
    public boolean existsAuthUserByUsernameAndPassword(String username, String password) {
        return authUserRepository.existsByUsernameAndPassword(username,password);
    }

    @Override
    public AuthUser updateAuthUser(AuthUser authUser) {
        return authUserRepository.save(authUser);
    }

    @Override
    public void deleteAuthUserById(UUID uuid) {
        authUserRepository.deleteById(uuid);
    }

    @Override
    public void deleteAuthUser(AuthUser authUser) {
        authUserRepository.delete(authUser);
    }
}
