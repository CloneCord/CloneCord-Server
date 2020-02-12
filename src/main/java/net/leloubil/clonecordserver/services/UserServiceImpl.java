package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.authentication.AuthUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.persistence.AuthUserRepository;
import net.leloubil.clonecordserver.persistence.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthUserService authUserService;

    public UserServiceImpl(UserRepository userRepository, AuthUserService authUserService) {
        this.userRepository = userRepository;
        this.authUserService = authUserService;
    }

    @Override
    public User createUser(AuthUser userData) {
        UUID uuid = UUID.randomUUID();
        User u = User.builder()
                .id(uuid)
                .username(userData.getUsername())
                .displayName(userData.getUsername())
                .build();
        userData.setUuid(uuid);
        authUserService.createAuthUser(userData);
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
