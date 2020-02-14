package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.persistence.GuildRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GuildsServiceImpl implements GuildsService {

    private final GuildRepository guildRepository;

    public GuildsServiceImpl(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }


    @Override
    public Guild createGuild(Guild g, User owner) {
        g.setId(UUID.randomUUID());
        g.addMember(owner,true);
        return guildRepository.insert(g);
    }

    @Override
    public Optional<Guild> getGuildById(UUID id) {
        return guildRepository.findById(id);
    }

    @Override
    public Optional<Guild> getGuildByName(String name) {
        return guildRepository.findByName(name);
    }

    @Override
    public List<Guild> getGuildsContaining(User user) {
        return guildRepository.findAllByMembersContainingId(user.getId());
    }

    @Override
    public Guild updateGuild(Guild guild) {
        return guildRepository.save(guild);
    }

    @Override
    public void deleteGuild(Guild guid) {
        guildRepository.delete(guid);
    }

    @Override
    public void deleteGuildById(UUID guildId) {
        guildRepository.deleteById(guildId);
    }

}
