package net.leloubil.clonecordserver.persistence;

import net.leloubil.clonecordserver.data.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository  extends MongoRepository<Message, UUID> {


    Page<Message> findAllByChannelIdAndSentDateBefore(UUID channelId, Date before, Pageable pageable);


    Page<Message> findAllByChannelIdAndSentDateAfter(UUID channelId,Date after,Pageable pageable);

    Page<Message> findAllByChannelId(UUID channelId, Pageable pageable);


    Optional<Message> findByIdAndChannelId(UUID messageId, UUID channelId);

}
