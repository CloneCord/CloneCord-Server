package net.leloubil.clonecordserver.formdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormReaction {

    /*Not sure how to store emotes and allows users to use other guilds emotes*/
    @NotNull @ApiModelProperty( value = "Emote id", required = true)
    long emoteId;
}
