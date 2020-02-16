package net.leloubil.clonecordserver.formdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.data.Channel;
import net.leloubil.clonecordserver.data.Member;
import net.leloubil.clonecordserver.data.Role;
import net.leloubil.clonecordserver.data.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 2,max = 15)
    String name;

}
