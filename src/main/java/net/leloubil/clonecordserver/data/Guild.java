package net.leloubil.clonecordserver.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormGuild;
import net.leloubil.clonecordserver.services.GuildsService;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
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

    HashSet<Role> roles = new HashSet<>();

    HashSet<Member> members = new HashSet<>();

    HashSet<Channel> channels = new HashSet<>();

    public static Guild getGuild(UUID guildId, GuildsService guildsService) {
        return guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    public Member getMember(UUID memberId) {
        return getMembers().stream().filter(m -> m.getId().equals(memberId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("memberId"));
    }

    public void addMember(User user) {
        addMember(user, false);
    }

    public void addMember(User user, boolean owner) {
        Member m = new Member(user);
        m.setOwner(owner);
        members.add(m);
    }

    public Optional<Channel> getChannel(UUID channelId) {
        return getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst();
    }

    public boolean isOwner(UUID uuid) {
        return getMembers().stream().anyMatch(s -> s.getId().equals(uuid) && s.isOwner());
    }

    public Role getRole(UUID roleId) {
        return getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("roleId"));
    }
}
