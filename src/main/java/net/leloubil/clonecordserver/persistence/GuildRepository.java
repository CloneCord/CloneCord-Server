package net.leloubil.clonecordserver.persistence;

import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuildRepository extends MongoRepository<Guild, UUID> {

    @Query("{'members.id' : ?0}")
    List<Guild> findAllByMembersContainingId(UUID id);

    Optional<Guild> findByName(String name);

    boolean existsByName(String name);

}
