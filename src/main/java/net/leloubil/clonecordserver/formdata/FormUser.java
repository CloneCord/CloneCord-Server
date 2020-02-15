package net.leloubil.clonecordserver.formdata;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.validation.UniqueUsername;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * This class represent an User on the infrastructure
 * An user is someone that uses the services, an Account.
 * */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormUser {

    @NotEmpty
    @Indexed
    @UniqueUsername
    String username;

    String avatar;

}
