package net.leloubil.clonecordserver.controllers;

import net.leloubil.clonecordserver.data.*;
import net.leloubil.clonecordserver.exceptions.ConflictException;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.formdata.FormChannel;
import net.leloubil.clonecordserver.formdata.FormGuild;
import net.leloubil.clonecordserver.formdata.FormMessage;
import net.leloubil.clonecordserver.formdata.FormRole;
import net.leloubil.clonecordserver.persistence.MessageRepository;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Guild createGuild(@Validated @RequestBody FormGuild guild){
        User owner = LoginUser.getCurrent().getUser(userService);
        return guildsService.createGuild(guild, owner);
    }

    @GetMapping("/{guildId}")
    public Guild getGuildInfo(@PathVariable UUID guildId){
        return guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @PutMapping("/{guildId}")
    public Guild updateGuild(@PathVariable UUID guildId,@RequestBody FormGuild newGuild){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        BeanUtils.copyProperties(newGuild,g);
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
        public Channel createChannel(@PathVariable UUID guildId, @RequestBody @Validated FormChannel channel){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            Channel c = new Channel();
            BeanUtils.copyProperties(channel,c);
            g.getChannels().add(c);
            guildsService.updateGuild(g);
            return c;
        }

        @PutMapping("/{channelId}")
        public Channel updateChannel(@PathVariable UUID guildId,@PathVariable UUID channelId, @RequestBody @Validated FormChannel channel){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            Channel chan = g.getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("channelId"));

            g.getChannels().remove(chan);
            BeanUtils.copyProperties(channel,chan);
            g.getChannels().add(chan);
            guildsService.updateGuild(g);
            return chan;
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
            public Message sendMessage(@PathVariable UUID guildId,@PathVariable UUID channelId, @RequestBody @Validated FormMessage message){
                UUID senderId = LoginUser.getCurrent().getUuid();
                Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                Channel c = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
                Message m = new Message();
                BeanUtils.copyProperties(message,m);
                m.setSenderId(senderId);
                m.setChannelId(c.getChannelId());
                messageRepository.save(m);
                return m;
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
        public Role createRole(@PathVariable UUID guildId, @RequestBody @Validated FormRole role){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            if(g.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))){
                throw new ConflictException("roleName");
            }
            Role r = new Role();
            BeanUtils.copyProperties(role,r);
            g.getRoles().add(r);
            guildsService.updateGuild(g);
            return r;
        }

        @PutMapping("/{roleId}")
        public Role updateRole(@PathVariable UUID guildId,@PathVariable UUID roleId, @RequestBody @Validated FormRole role){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            Role ro = g.getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("roleId"));
            g.getRoles().remove(ro);
            BeanUtils.copyProperties(role,ro);
            g.getRoles().add(ro);
            guildsService.updateGuild(g);
            return ro;
        }

        @DeleteMapping("/{roleId}")
        public void deleteRole(@PathVariable UUID guildId, @PathVariable UUID roleId){
            Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
            g.getRoles().removeIf(c -> c.getId().equals(roleId));
            guildsService.updateGuild(g);
        }

    }

}
