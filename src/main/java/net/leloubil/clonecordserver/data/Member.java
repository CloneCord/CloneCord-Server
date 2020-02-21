package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashSet;
import java.util.UUID;

/**
* This class represent an user in a Guild
 * This isn't an {@link User}
*/

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {

    @Id
    UUID id = UUID.randomUUID();

    @Indexed
    String username;

    boolean isOwner;

    HashSet<Role> roles = new HashSet<>();

    public Member(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
