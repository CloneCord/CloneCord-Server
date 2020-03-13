package net.leloubil.clonecordserver.data.internal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("externalInstances")
public class ExternalInstance {

    @Id
    String serverAddress;

    String publicKey;

    boolean waitingForKey;
}
