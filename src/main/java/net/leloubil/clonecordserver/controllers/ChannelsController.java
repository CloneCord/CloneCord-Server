package net.leloubil.clonecordserver.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.leloubil.clonecordserver.data.Channel;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.Message;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormChannel;
import net.leloubil.clonecordserver.formdata.FormMessage;
import net.leloubil.clonecordserver.persistence.MessageRepository;
import net.leloubil.clonecordserver.services.GuildsService;
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
@SuppressWarnings("deprecation")
@RequestMapping("/guilds/{guildId}")
@Api( tags = "Channels", description = "Operations on channels")
public class ChannelsController {

    private GuildsService guildsService;

    public ChannelsController(GuildsService guildsService) {
        this.guildsService = guildsService;
    }

    @PostMapping
    @ApiOperation("Creates a new Channel in specified Guild")
    public Channel createChannel(@PathVariable @ApiParam("ID of the specified Guild") UUID guildId, @RequestBody @Validated @ApiParam("Channel data") FormChannel channel){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel c = new Channel();
        BeanUtils.copyProperties(channel,c);
        g.getChannels().add(c);
        guildsService.updateGuild(g);
        return c;
    }


    @ApiOperation("Updates specified Channel if current User has permissions")
    @PutMapping("/{channelId}")
    public Channel updateChannel(@PathVariable @ApiParam("ID of the specified Guild") UUID guildId,@ApiParam("ID of the specified Channel") @PathVariable UUID channelId, @RequestBody @Validated @ApiParam("New channel data") FormChannel channel){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel chan = g.getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("channelId"));

        g.getChannels().remove(chan);
        BeanUtils.copyProperties(channel,chan);
        g.getChannels().add(chan);
        guildsService.updateGuild(g);
        return chan;
    }

    @DeleteMapping("/{channelId}")
    @ApiOperation("Deletes specified Channel if current User has permissions")
    public void deleteChannel(@PathVariable @ApiParam("ID of the specified Guild") UUID guildId, @ApiParam("ID of the specified Channel") @PathVariable UUID channelId){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        g.getChannels().removeIf(c -> c.getChannelId().equals(channelId));
        guildsService.updateGuild(g);
    }

}
