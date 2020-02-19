package net.leloubil.clonecordserver.formdata;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.validation.UniqueUsername;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @NotBlank
    @Indexed
    @UniqueUsername
    @Size(min = 2,max = 15)
    String username;


    String avatar;

}
