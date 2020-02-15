package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.formdata.FormGuild;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuildsService{

    Guild createGuild(FormGuild g, User owner);

    Optional<Guild> getGuildById(UUID id);

    Optional<Guild> getGuildByName(String name);

    List<Guild> getGuildsContaining(User user);

    Guild updateGuild(Guild guild);

    void deleteGuild(Guild guild);

    void deleteGuildById(UUID guildId);

    

}
