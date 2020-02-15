package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.formdata.FormChannel;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Channel extends FormChannel {

    @Id
    UUID channelId = UUID.randomUUID();

}
