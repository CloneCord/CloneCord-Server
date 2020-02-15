package net.leloubil.clonecordserver.formdata;

import io.swagger.annotations.ApiModelProperty;
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
    @UniqueEmail @ApiModelProperty( value = "User email", required = true)
    String email;

    @NotEmpty
    @ValidPassword @ApiModelProperty( value = "User password", required = true)
    String password;
}
