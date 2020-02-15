package net.leloubil.clonecordserver.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@SuppressWarnings("deprecation")
@RequestMapping("/guilds/{guildId}/roles")
@Api( tags = "Roles API", description = "Operations on roles")
public class RolesController {

    private GuildsService guildsService;

    public RolesController(GuildsService guildsService) {
        this.guildsService = guildsService;
    }

    @PostMapping
    @ApiOperation("Creates a new Role in specified Guild if current User has permissions")
    public Role createRole(@ApiParam("ID of the specified Guild") @PathVariable UUID guildId, @RequestBody @Validated @ApiParam("Role data") FormRole role){
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
    @ApiOperation("Updates specified Role in specified Guild if current User has permissions")
    public Role updateRole(@ApiParam("ID of the specified Guild") @PathVariable UUID guildId,@PathVariable UUID roleId, @RequestBody @Validated @ApiParam("New role data") FormRole role){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        Role ro = g.getRoles().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new RessourceNotFoundException("roleId"));
        g.getRoles().remove(ro);
        BeanUtils.copyProperties(role,ro);
        g.getRoles().add(ro);
        guildsService.updateGuild(g);
        return ro;
    }

    @DeleteMapping("/{roleId}")
    @ApiOperation("Deletes specified Role in specified Guild if current User has permissions")
    public void deleteRole(@ApiParam("ID of the specified Guild") @PathVariable UUID guildId, @PathVariable @ApiParam("ID of the specified Role") UUID roleId){
        Guild g = guildsService.getGuildById(guildId).orElseThrow(() -> new RessourceNotFoundException("guildId"));
        g.getRoles().removeIf(c -> c.getId().equals(roleId));
        guildsService.updateGuild(g);
    }

}
