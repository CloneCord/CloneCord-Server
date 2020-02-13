package net.leloubil.clonecordserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class UserNotFoundException extends RuntimeException {

    UUID userId;
    String userName;

    public UserNotFoundException(UUID userId, String userName) {
        super(String.format("User not found with id: %s, userName: %s", userId,userName));
        this.userId = userId;
        this.userName = userName;
    }

}
