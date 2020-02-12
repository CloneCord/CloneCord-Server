package net.leloubil.clonecordserver.persistence;

import net.leloubil.clonecordserver.authentication.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends MongoRepository<AuthUser, UUID> {

    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByUsernameAndPassword(String username, String password);

    boolean existsByUsernameAndPassword(String username, String password);

}
