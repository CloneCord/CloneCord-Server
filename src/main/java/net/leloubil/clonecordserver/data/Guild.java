package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.formdata.FormGuild;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Guilds")
public class Guild extends FormGuild {

    @Id
    UUID id = UUID.randomUUID();

    List<Role> roles = new ArrayList<>();

    List<Member> members = new ArrayList<>();

    List<Channel> channels = new ArrayList<>();

    public void addMember(User user){
        addMember(user,false);
    }

    public void addMember(User user,boolean owner){
        Member m = new Member(user);
        m.setOwner(owner);
        members.add(m);
    }

    public Optional<Channel> getChannel(UUID channelId) {
        return getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst();
    }
}
