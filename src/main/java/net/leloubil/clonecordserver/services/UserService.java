package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.RegistrationUser;
import net.leloubil.clonecordserver.data.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UniqueProof<String,User> {

    User createUser(RegistrationUser userData);

    Optional<User> getUser(UUID userId);

    Optional<User> getUserByName(String username);

    User updateUser(User user);

    void deleteUserById(UUID userId);

    void deleteUser(User user);

}
