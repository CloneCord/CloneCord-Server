package net.leloubil.clonecordserver.formdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.validation.UniqueEmail;
import net.leloubil.clonecordserver.validation.ValidPassword;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormLogin {

    @NotEmpty
    @Indexed
    @UniqueEmail
    String email;

    @NotEmpty
    @ValidPassword
    String password;
}
