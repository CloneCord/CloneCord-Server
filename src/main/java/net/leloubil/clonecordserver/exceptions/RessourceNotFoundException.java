package net.leloubil.clonecordserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class RessourceNotFoundException extends RuntimeException {


    public RessourceNotFoundException(String errorField) {
        super(String.format("Ressource not found for field %s",errorField));
    }

    public RessourceNotFoundException() {
        super("Ressource not found");
    }

}
