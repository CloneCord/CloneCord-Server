package net.leloubil.clonecordserver.formdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.data.Reaction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document("Messages")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormMessage {

    @NotEmpty
    String message;

}
