package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.authentication.AuthUser;
import net.leloubil.clonecordserver.data.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(AuthUser userData);

    Optional<User> getUser(UUID userId);

    Optional<User> getUserByName(String username);

    User updateUser(User user);

    void deleteUserById(UUID userId);

    void deleteUser(User user);

}
