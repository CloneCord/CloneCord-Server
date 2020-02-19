package net.leloubil.clonecordserver.formdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.validation.UniqueEmail;
import net.leloubil.clonecordserver.validation.ValidPassword;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormLogin {

    @NotBlank
    @Indexed
    @UniqueEmail
    @Size(min = 5, max = 20)
    String email;

    @NotEmpty
    @ValidPassword
    @Size(min = 5,max = 20)
    String password;
}
