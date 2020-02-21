package net.leloubil.clonecordserver.security;

import lombok.extern.apachecommons.CommonsLog;
import net.leloubil.clonecordserver.data.*;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class GuildPermissionCheck {

    private final UserService userService;

    private final GuildsService guildsService;


    public GuildPermissionCheck(UserService userService, GuildsService guildsService) {
        this.userService = userService;
        this.guildsService = guildsService;
    }


    public boolean hasPermission(String perm, UUID guildId) {
        log.info("Checking if user has " + perm + " on " + guildId);
        Member member = getMember(guildId);
        if (member == null) {
            log.info("false");
            return false;

        }
        if (member.isOwner()) {
            log.info("true");
            return true;
        }
        List<Permissions> perms = member.getRoles().stream().collect(Collectors.flatMapping((Role role) -> role.getRolePermissions().stream(), Collectors.toList()));
        if (perms.stream().anyMatch(p -> p.equals(Permissions.ADMINISTRATOR) || p.name().equals(perm))) {
            log.info("true");
            return true;
        } else {
            log.info("false");
            return false;
        }
    }

    public boolean isOwner(UUID guildId) {
        log.info("Checking if user is owner");
        Member member = getMember(guildId);
        if (member == null) {
            log.info("false");
            return false;
        }
        if (member.isOwner()) {
            log.info("true");
            return true;
        } else {
            log.info("false");
            return false;
        }
    }

    private Member getMember(UUID guildId) {
        LoginUser current = LoginUser.getCurrent();
        Optional<Guild> g = guildsService.getGuildById(guildId);
        if (g.isEmpty()) {
            return null;
        }
        Guild guild = g.get();
        Optional<Member> me = guild.getMembers().stream().filter(m -> m.getId().equals(current.getUuid())).findAny();
        return me.orElse(null);
    }

    public boolean isMember(UUID guildId) {
        return getMember(guildId) != null;
    }
}
