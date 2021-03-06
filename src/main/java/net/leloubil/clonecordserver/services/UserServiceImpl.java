package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.formdata.RegistrationUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.persistence.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final LoginUserService LoginUserService;

    public UserServiceImpl(UserRepository userRepository, LoginUserService LoginUserService) {
        this.userRepository = userRepository;
        this.LoginUserService = LoginUserService;
    }

    @Override
    public User createUser(RegistrationUser userData) {
        User u = new User();
        BeanUtils.copyProperties(userData,u);
        LoginUser lu = LoginUserService.createLoginUser(userData);
        u.setId(lu.getUuid());
        return userRepository.save(u);
    }

    @Override
    public Optional<User> getUser(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }


}
