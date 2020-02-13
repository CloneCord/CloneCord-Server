package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.authentication.AuthUser;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserService {

    AuthUser createAuthUser(AuthUser authUser);

    Optional<AuthUser> getAuthUserByEmail(String email);

    boolean existsAuthUserByEmailAndPassword(String email, String password);

    AuthUser updateAuthUser(AuthUser authUser);

    void deleteAuthUserById(UUID uuid);

    void deleteAuthUser(AuthUser authUser);

}
