package net.leloubil.clonecordserver.authentication;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


/**
 * Class to represent data needed to login or register
 */
@Data @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUser {

    String username;

    String password;

    UUID uuid;

}
