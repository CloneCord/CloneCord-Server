package net.leloubil.clonecordserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
@Getter
public class ConflictException extends RuntimeException{

    public ConflictException(String data) {
        super("Conflict : " + data);
    }
}
