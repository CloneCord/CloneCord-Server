package net.leloubil.clonecordserver.formdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;


@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormChannel {

    @NotEmpty
    String name;


}
