package net.leloubil.clonecordserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.Role;
import net.leloubil.clonecordserver.exceptions.ConflictException;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormRole;
import net.leloubil.clonecordserver.services.GuildsService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/guilds/{guildId}/roles")
@Tag(name = "Roles", description = "Operations on roles")
public class RolesController {

    private GuildsService guildsService;

    public RolesController(GuildsService guildsService) {
        this.guildsService = guildsService;
    }

    @PostMapping
    @Operation(description = "Creates a new Role in specified Guild if current User has permissions")
    public Role createRole(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @RequestBody @Validated @Parameter(description = "Role data", required = true) FormRole role) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        if (g.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))) {
            throw new ConflictException("roleName");
        }
        Role r = new Role();
        BeanUtils.copyProperties(role, r);
        g.getRoles().add(r);
        guildsService.updateGuild(g);
        return r;
    }

    @PutMapping("/{roleId}")
    @Operation(description = "Updates specified Role in specified Guild if current User has permissions")
    public Role updateRole(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @PathVariable UUID roleId, @RequestBody @Validated @Parameter(description = "New role data", required = true) FormRole role) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Role ro = g.getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("roleId"));
        g.getRoles().remove(ro);
        BeanUtils.copyProperties(role, ro);
        g.getRoles().add(ro);
        guildsService.updateGuild(g);
        return ro;
    }

    @DeleteMapping("/{roleId}")
    @Operation(description = "Deletes specified Role in specified Guild if current User has permissions")
    public void deleteRole(@Parameter(description = "ID of the specified Guild", required = true) @PathVariable UUID guildId, @PathVariable @Parameter(description = "ID of the specified Role", required = true) UUID roleId) {
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        g.getRoles().removeIf(c -> c.getId().equals(roleId));
        guildsService.updateGuild(g);
    }

}
