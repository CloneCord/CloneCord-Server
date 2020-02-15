package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;
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

    List<Role> roles = new ArrayList<>();

    public Member(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
