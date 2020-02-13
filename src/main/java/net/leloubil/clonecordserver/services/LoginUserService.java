package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.LoginUser;

import java.util.Optional;
import java.util.UUID;

public interface LoginUserService extends UniqueProof<String,LoginUser> {

    LoginUser createLoginUser(LoginUser loginUser);

    Optional<LoginUser> getLoginUserByEmail(String email);

    Optional<LoginUser> getLoginUserById(UUID id);

    boolean existsLoginUserByEmailAndPassword(String email, String password);

    LoginUser updateLoginUser(LoginUser loginUser);

    void deleteLoginUserById(UUID uuid);

    void deleteLoginUser(LoginUser loginUser);

}
