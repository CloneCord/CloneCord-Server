package net.leloubil.clonecordserver.data;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.formdata.FormRole;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.UUID;

/**
 * This class represent a Role in a Guild
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends FormRole {

    @Id
    UUID id = UUID.randomUUID();

    HashSet<Permissions> rolePermissions = new HashSet<>();

}
