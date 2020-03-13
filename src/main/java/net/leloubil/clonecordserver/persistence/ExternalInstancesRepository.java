package net.leloubil.clonecordserver.persistence;

import net.leloubil.clonecordserver.data.internal.ExternalInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExternalInstancesRepository extends MongoRepository<ExternalInstance, String> {


}
