package net.leloubil.clonecordserver.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.Member;
import net.leloubil.clonecordserver.data.Role;
import net.leloubil.clonecordserver.services.GuildsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/guilds/{guildId}/members/{memberId}/roles")
@Tag(name = "Members", description = "Operations on a guild's members")
public class MembersRolesController {

    private final GuildsService guildsService;

    public MembersRolesController(GuildsService guildsService) {
        this.guildsService = guildsService;
    }


    @DeleteMapping
    @PreAuthorize("@guildPermissionCheck.hasPermission('ADMINISTRATOR',#guildId)")
    public void removeRole(@PathVariable UUID guildId, @PathVariable UUID memberId, UUID roleId) {
        Guild g = Guild.getGuild(guildId, guildsService);
        Role r = g.getRole(roleId);
        Member m = g.getMember(memberId);
        m.getRoles().remove(r);
    }

    @PatchMapping
    @PreAuthorize("@guildPermissionCheck.hasPermission('ADMINISTRATOR',#guildId)")
    public void addRole(@PathVariable UUID guildId, @PathVariable UUID memberId, UUID roleId) {
        Guild g = Guild.getGuild(guildId, guildsService);
        Role r = g.getRole(roleId);
        Member m = g.getMember(memberId);
        m.getRoles().add(r);
    }


}
