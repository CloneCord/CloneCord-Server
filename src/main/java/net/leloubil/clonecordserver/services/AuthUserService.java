package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.authentication.AuthUser;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserService {

    AuthUser createAuthUser(AuthUser authUser);

    Optional<AuthUser> getAuthUserByUsername(String username);

    boolean existsAuthUserByUsernameAndPassword(String username, String password);

    AuthUser updateAuthUser(AuthUser authUser);

    void deleteAuthUserById(UUID uuid);

    void deleteAuthUser(AuthUser authUser);

}
