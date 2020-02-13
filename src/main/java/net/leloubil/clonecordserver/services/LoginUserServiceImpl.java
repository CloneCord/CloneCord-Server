package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.persistence.LoginUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoginUserServiceImpl implements LoginUserService {

    private final LoginUserRepository LoginUserRepository;

    public LoginUserServiceImpl(LoginUserRepository LoginUserRepository) {
        this.LoginUserRepository = LoginUserRepository;
    }


    @Override
    public LoginUser createLoginUser(LoginUser loginUser) {
        return LoginUserRepository.insert(loginUser);
    }

    @Override
    public Optional<LoginUser> getLoginUserByEmail(String email) {
        return LoginUserRepository.findByEmail(email);
    }

    @Override
    public Optional<LoginUser> getLoginUserById(UUID id) {
        return LoginUserRepository.findById(id);
    }

    @Override
    public boolean existsLoginUserByEmailAndPassword(String email, String password) {
        return LoginUserRepository.existsByEmailAndPassword(email,password);
    }

    @Override
    public LoginUser updateLoginUser(LoginUser loginUser) {
        return LoginUserRepository.save(loginUser);
    }

    @Override
    public void deleteLoginUserById(UUID uuid) {
        LoginUserRepository.deleteById(uuid);
    }

    @Override
    public void deleteLoginUser(LoginUser loginUser) {
        LoginUserRepository.delete(loginUser);
    }
}
