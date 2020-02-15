package net.leloubil.clonecordserver.formdata;


import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * This class represent a Role in a Guild
 * */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormRole {

    @Indexed
    @NotEmpty @ApiModelProperty( value = "Role name", required = true)
    String name;

    @NotEmpty @ApiModelProperty( value = "Role color", required = true)
    String hexColor;

}
