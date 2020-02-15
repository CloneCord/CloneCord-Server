package net.leloubil.clonecordserver.formdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.data.Channel;
import net.leloubil.clonecordserver.data.Member;
import net.leloubil.clonecordserver.data.Role;
import net.leloubil.clonecordserver.data.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Guilds")
public class FormGuild {

    @Indexed
    @NotEmpty
    String name;

}
