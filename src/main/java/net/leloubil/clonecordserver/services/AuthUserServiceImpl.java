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
    public Optional<AuthUser> getAuthUserByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    @Override
    public boolean existsAuthUserByEmailAndPassword(String email, String password) {
        return authUserRepository.existsByEmailAndPassword(email,password);
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
