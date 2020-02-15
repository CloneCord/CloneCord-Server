package net.leloubil.clonecordserver.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@SuppressWarnings("deprecation")
@RequestMapping("/guilds")
@Api( tags = "Guilds", description = "Operations on guilds")
public class GuildsController {

    private final GuildsService guildsService;
    private UserService userService;

    public GuildsController(GuildsService guildsService, UserService userService) {
        this.guildsService = guildsService;
        this.userService = userService;
    }


    @PostMapping("")
    @ApiOperation("Creates a new Guild")
    public Guild createGuild(@Validated @RequestBody FormGuild guild){
        User owner = LoginUser.getCurrent().getUser(userService);
        return guildsService.createGuild(guild, owner);
    }

    @GetMapping("/{guildId}")
    @ApiOperation("Gets info of a specific Guild")
    public Guild getGuildInfo(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId){
        return guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @PutMapping("/{guildId}")
    @ApiOperation("Updates an owned Guild")
    public Guild updateGuild(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId,@RequestBody @Validated @ApiParam(value = "New guild data", required = true) FormGuild newGuild){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        BeanUtils.copyProperties(newGuild,g);
        return guildsService.updateGuild(g);
    }

    @DeleteMapping("/{guildId}")
    @ApiOperation("Deletes an owned Guild")
    public void deleteGuild(@ApiParam(value = "ID of the specified Guild", required = true) @PathVariable UUID guildId){
        guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }


}
