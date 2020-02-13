package net.leloubil.clonecordserver.persistence;

import net.leloubil.clonecordserver.data.LoginUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginUserRepository extends MongoRepository<LoginUser, UUID> {

    Optional<LoginUser> findByEmail(String email);

    Optional<LoginUser> findByEmailAndPassword(String email, String password);

    boolean existsByEmailAndPassword(String username, String password);

}
