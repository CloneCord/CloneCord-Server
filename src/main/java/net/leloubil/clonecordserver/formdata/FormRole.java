package net.leloubil.clonecordserver.formdata;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

/**
 * This class represent a Role in a Guild
 * */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormRole {

    @Indexed
    String name;

    String displayName;

    String hexColor;

}
