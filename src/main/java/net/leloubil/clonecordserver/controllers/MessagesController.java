package net.leloubil.clonecordserver.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/guilds/{guildId}/{channelId}/messages")
@Tag(name = "Messages", description = "Operations on messages")
public class MessagesController {


    private MessageRepository messageRepository;
    private GuildsService guildsService;

    public MessagesController(MessageRepository messageRepository, GuildsService guildsService) {
        this.messageRepository = messageRepository;
        this.guildsService = guildsService;
    }

    @PostMapping
    @Operation(description = "Sends a new message to specified Channel if current User has permissions")
    public Message sendMessage(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @Parameter(description = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @RequestBody @Validated @Parameter(description = "Message data", required = true) FormMessage message) {
        UUID senderId = LoginUser.getCurrent().getUuid();
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel c = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Message m = new Message();
        BeanUtils.copyProperties(message, m);
        m.setSenderId(senderId);
        m.setChannelId(c.getChannelId());
        messageRepository.save(m);
        return m;
    }

    @DeleteMapping("/{messageId}")
    @Operation(description = "Deletes specified message in specified Channel if current User has permissions")
    public void deleteMessage(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @Parameter(description = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @PathVariable @Parameter(description = "ID of the specified Message", required = true) UUID messageId) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel chan = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("channelId"));
        Message m = messageRepository.findByIdAndChannelId(messageId, chan.getChannelId()).orElseThrow(() -> new RessourceNotFoundException("messageId"));
        messageRepository.delete(m);
    }

    @GetMapping()
    @Operation(description = "Gets a list of messages in specified Channel if current User has permissions")
    public List<Message> getMessages(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @Parameter(description = "ID of the specified Channel", required = true) @PathVariable UUID channelId, @RequestParam(value = "limit", required = false) @Parameter(description = "Maximum number of messages to return (max = 100)") Integer limit, @RequestParam(value = "before", required = false) @Parameter(description = "Return only messages sent before this timestamp") Long before, @RequestParam(value = "after", required = false) @Parameter(description = "Return only mesages after this timestamp") Long after) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Channel c = g.getChannel(channelId).orElseThrow(() -> new RessourceNotFoundException("channelId"));
        if (limit == null) {
            limit = 100;
        }
        Date beforeDate = before == null ? new Date() : new Date(before * 1000);
        if (after == null) {
            return messageRepository.findAllByChannelIdAndSentDateBefore(c.getChannelId(), beforeDate, PageRequest.of(0, limit, Sort.Direction.ASC, "sentDate")).toList();
        } else {
            return messageRepository.findAllByChannelIdAndSentDateAfter(c.getChannelId(), new Date(after * 1000), PageRequest.of(0, limit, Sort.Direction.ASC, "sentDate")).toList();
        }
    }

}
