package net.leloubil.clonecordserver.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.leloubil.clonecordserver.data.Channel;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.Message;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormMessage;
import net.leloubil.clonecordserver.persistence.MessageRepository;
import net.leloubil.clonecordserver.services.GuildsService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/guilds/{guildId}/{channelId}/messages")
@Api( tags = "Messages", description = "Operations on messages")
public class MessagesController {


    private MessageRepository messageRepository;
    private GuildsService guildsService;

    public MessagesController(MessageRepository messageRepository, GuildsService guildsService) {
        this.messageRepository = messageRepository;
        this.guildsService = guildsService;
    }

    @PostMapping
    @ApiOperation("Sends a new message to specified Channel if current User has permissions")
    public Message sendMessage(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @ApiParam(value = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @RequestBody @Validated @ApiParam(value = "Message data", required = true) FormMessage message){
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
    @ApiOperation("Deletes specified message in specified Channel if current User has permissions")
    public void deleteMessage(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId,@ApiParam(value = "ID of the specified Channel", required = true) @PathVariable UUID channelId,@PathVariable @ApiParam(value = "ID of the specified Message", required = true) UUID messageId){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel chan = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("channelId"));
        Message m = messageRepository.findByIdAndChannelId(messageId,chan.getChannelId()).orElseThrow(() -> new RessourceNotFoundException("messageId"));
        messageRepository.delete(m);
    }

    @GetMapping()
    @ApiOperation("Gets a list of messages in specified Channel if current User has permissions")
    public List<Message> getMessages(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @ApiParam(value = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @RequestParam(value = "limit", required = false) @ApiParam(value = "Maximum number of messages to return (max = 100)",defaultValue = "100") Integer limit, @RequestParam(value = "before", required = false) @ApiParam(value = "Return only messages sent before this timestamp", format = "timestamp") Long before, @RequestParam(value = "after", required = false) @ApiParam(value = "Return only mesages after this timestamp", format = "timestamp") Long after){
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
