package net.leloubil.clonecordserver.controllers;

import net.leloubil.clonecordserver.data.*;
import net.leloubil.clonecordserver.exceptions.ConflictException;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.LoginUser;
import net.leloubil.clonecordserver.persistence.MessageRepository;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    @RequestMapping("/guilds/{guildId}")
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

        @RestController
        @RequestMapping("/guilds/{guildId}/{channelId}/messages")
        public class GuildMessages{


            @Autowired
            private MessageRepository messageRepository;

            @PostMapping
            public Message sendMessage(@PathVariable UUID guildId,@PathVariable UUID channelId, @RequestBody @Validated Message message){
                UUID senderId = LoginUser.getCurrent().getUuid();
                Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                Channel c = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                message.setId(UUID.randomUUID());
                message.setSenderId(senderId);
                message.setChannelId(c.getChannelId());
                message.setSentDate(new Date());
                messageRepository.save(message);
                return message;
            }

            @DeleteMapping("/{messageId}")
            public void deleteMessage(@PathVariable UUID guildId,@PathVariable UUID channelId,@PathVariable UUID messageId){
                Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                Channel chan = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("channelId"));
                Message m = messageRepository.findByIdAndChannelId(messageId,chan.getChannelId()).orElseThrow(() -> new RessourceNotFoundException("messageId"));
                messageRepository.delete(m);
            }

            @GetMapping()
            public List<Message> getMessages(@PathVariable UUID guildId, @PathVariable UUID channelId, @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "before", required = false) Long before, @RequestParam(value = "after", required = false) Long after){
                Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                Channel c = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("channelId"));
                if(limit == null){
                    limit = 100;
                }
                Date beforeDate = before == null ? new Date() : new Date(before * 1000);
                if(after == null){
                    return messageRepository.findAllByChannelIdAndSentDateBefore(c.getChannelId(),beforeDate, PageRequest.of(0,limit, Sort.Direction.ASC,"sentDate")).toList();
                }
                else {
                    return messageRepository.findAllByChannelIdAndSentDateAfter(c.getChannelId(),new Date(after * 1000), PageRequest.of(0,limit, Sort.Direction.ASC,"sentDate")).toList();
                }
            }

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
