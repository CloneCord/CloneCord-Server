package net.leloubil.clonecordserver.controllers;

import net.leloubil.clonecordserver.data.*;
import net.leloubil.clonecordserver.exceptions.ConflictException;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.LoginUser;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/guilds")
public class GuildsController {

    private final GuildsService guildsService;
    private UserService userService;

    public GuildsController(GuildsService guildsService, UserService userService) {
        this.guildsService = guildsService;
        this.userService = userService;
    }


    @PostMapping("")
    public Guild createGuild(@Validated @RequestBody Guild guild){
        User owner = LoginUser.getCurrent().getUser(userService);
        return guildsService.createGuild(guild, owner);
    }

    @GetMapping("/{guildId}")
    public Guild getGuildInfo(@PathVariable UUID guildId){
        return guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @PutMapping("/{guildId}")
    public Guild updateGuildName(@PathVariable UUID guildId,@RequestBody String newName){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        g.setName(newName);
        return guildsService.updateGuild(g);
    }

    @DeleteMapping("/{guildId}")
    public void deleteGuild(@PathVariable UUID guildId){
        guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @RestController
    @RequestMapping("/guilds/{guildId}/channels")
    public class GuildChannel{

        @PostMapping
        public Channel createChannel(@PathVariable UUID guildId, @RequestBody @Validated Channel channel){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            channel.setChannelId(UUID.randomUUID());
            g.getChannels().add(channel);
            guildsService.updateGuild(g);
            return channel;
        }

        @PutMapping("/{channelId}")
        public Channel updateChannel(@PathVariable UUID guildId,@PathVariable UUID channelId, @RequestBody @Validated Channel channel){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            Channel chan = g.getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("channelId"));
            channel.setChannelId(chan.getChannelId());
            g.getChannels().remove(chan);
            g.getChannels().add(channel);
            guildsService.updateGuild(g);
            return channel;
        }

        @DeleteMapping("/{channelId}")
        public void deleteChannel(@PathVariable UUID guildId, @PathVariable UUID channelId){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            g.getChannels().removeIf(c -> c.getChannelId().equals(channelId));
            guildsService.updateGuild(g);
        }

    }


    @RestController
    @RequestMapping("/guilds/{guildId}/roles")
    public class GuildRole{

        @PostMapping
        public Role createRole(@PathVariable UUID guildId, @RequestBody @Validated Role role){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            if(g.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))){
                throw new ConflictException("roleName");
            }
            role.setId(UUID.randomUUID());
            g.getRoles().add(role);
            guildsService.updateGuild(g);
            return role;
        }

        @PutMapping("/{roleId}")
        public Role updateRole(@PathVariable UUID guildId,@PathVariable UUID roleId, @RequestBody @Validated Role role){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            Role ro = g.getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("roleId"));
            role.setId(ro.getId());
            g.getRoles().remove(ro);
            g.getRoles().add(role);
            guildsService.updateGuild(g);
            return role;
        }

        @DeleteMapping("/{roleId}")
        public void deleteChannel(@PathVariable UUID guildId, @PathVariable UUID roleId){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            g.getRoles().removeIf(c -> c.getId().equals(roleId));
            guildsService.updateGuild(g);
        }

    }

}
