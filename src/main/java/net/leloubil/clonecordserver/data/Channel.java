package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Channel {

    UUID channelId;

    @NotEmpty
    String name;


}
