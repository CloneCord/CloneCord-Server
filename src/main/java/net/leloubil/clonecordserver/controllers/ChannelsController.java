package net.leloubil.clonecordserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.Channel;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormChannel;
import net.leloubil.clonecordserver.services.GuildsService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/guilds/{guildId}")
@Tag(name = "Channels", description = "Operations on channels")
public class ChannelsController {

    private GuildsService guildsService;

    public ChannelsController(GuildsService guildsService) {
        this.guildsService = guildsService;
    }

    @PostMapping
    @Operation(description = "Creates a new Channel in specified Guild")
    public Channel createChannel(@PathVariable @Parameter(description = "ID of the specified Guild", required = true) UUID guildId, @RequestBody @Validated @Parameter(description = "Channel data", required = true) FormChannel channel) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel c = new Channel();
        BeanUtils.copyProperties(channel, c);
        g.getChannels().add(c);
        guildsService.updateGuild(g);
        return c;
    }


    @Operation(description = "Updates specified Channel if current User has permissions")
    @PutMapping("/{channelId}")
    public Channel updateChannel(@PathVariable @Parameter(description = "ID of the specified Guild", required = true) UUID guildId, @Parameter(description = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @RequestBody @Validated @Parameter(description = "New channel data", required = true) FormChannel channel) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel chan = g.getChannels().stream().filter(c -> c.getChannelId().equals(channelId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("channelId"));

        g.getChannels().remove(chan);
        BeanUtils.copyProperties(channel, chan);
        g.getChannels().add(chan);
        guildsService.updateGuild(g);
        return chan;
    }

    @DeleteMapping("/{channelId}")
    @Operation(description = "Deletes specified Channel if current User has permissions")
    public void deleteChannel(@PathVariable @Parameter(description = "ID of the specified Guild", required = true) UUID guildId, @Parameter(description = "ID of the specified Channel", required = true) @PathVariable UUID channelId) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        g.getChannels().removeIf(c -> c.getChannelId().equals(channelId));
        guildsService.updateGuild(g);
    }

}
