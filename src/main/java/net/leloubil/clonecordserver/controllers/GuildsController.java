package net.leloubil.clonecordserver.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.Member;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormGuild;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/guilds")
@Tag(name = "Guilds", description = "Operations on guilds")
public class GuildsController {

    private final GuildsService guildsService;
    private UserService userService;

    public GuildsController(GuildsService guildsService, UserService userService) {
        this.guildsService = guildsService;
        this.userService = userService;
    }


    @PostMapping("")
    @Operation(description = "Creates a new Guild")
    public Guild createGuild(@Validated @RequestBody FormGuild guild){
        User owner = LoginUser.getCurrent().getUser(userService);
        return guildsService.createGuild(guild, owner);
    }

    @GetMapping("/{guildId}")
    @Operation(description = "Gets info of a specific Guild")
    @PreAuthorize("@guildPermissionCheck.isMember(#guildId)")
    public Guild getGuildInfo(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId) {
        return guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @PutMapping("/{guildId}")
    @Operation(description = "Updates an owned Guild")
    @PreAuthorize("@guildPermissionCheck.hasPermission('ADMINISTRATOR',#guildId)")
    public Guild updateGuild(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @RequestBody @Validated @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New guild data", required = true) FormGuild newGuild) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        BeanUtils.copyProperties(newGuild, g);
        return guildsService.updateGuild(g);
    }

    @DeleteMapping("/{guildId}")
    @Operation(description = "Deletes an owned Guild")
    @PreAuthorize("@guildPermissionCheck.isOwner(#guildId)")
    public void deleteGuild(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId) {
        guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
    }

    @DeleteMapping("/{memberId}")
    @PreAuthorize("@guildPermissionCheck.hasPermission('ADMINISTRATOR',#guildId)")
    public void kickMember(@PathVariable UUID guildId, @PathVariable UUID memberId) {
        Guild g = Guild.getGuild(guildId, guildsService);
        Member me = g.getMember(memberId);
        g.getMembers().remove(me);
        guildsService.updateGuild(g);
    }


}
