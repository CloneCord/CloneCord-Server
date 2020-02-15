package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.formdata.FormReaction;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reaction extends FormReaction {


    @NotNull
    UUID authorUuid;
}
