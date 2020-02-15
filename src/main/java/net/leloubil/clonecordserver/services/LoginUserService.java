package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.formdata.FormLogin;

import java.util.Optional;
import java.util.UUID;

public interface LoginUserService{

    LoginUser createLoginUser(FormLogin loginUser);

    Optional<LoginUser> getLoginUserByEmail(String email);

    Optional<LoginUser> getLoginUserById(UUID id);

    boolean existsLoginUserByEmailAndPassword(String email, String password);

    LoginUser updateLoginUser(LoginUser loginUser);

    void deleteLoginUserById(UUID uuid);

    void deleteLoginUser(LoginUser loginUser);

}
