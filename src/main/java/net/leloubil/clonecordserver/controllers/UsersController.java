package net.leloubil.clonecordserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormUser;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for endpoint related to user data
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations on users")
public class UsersController {

    private final UserService userService;

    private final GuildsService guildsService;

    public UsersController(UserService userService, GuildsService guildsService) {
        this.userService = userService;
        this.guildsService = guildsService;
    }

    @GetMapping("/{userId}")
    @Operation(description = "Gets information about specified User")
    public User getUser(@PathVariable @Parameter(description = "ID of the user whose data is requested", required = true) UUID userId) {
        return userService.getUser(userId).orElseThrow(() -> new RessourceNotFoundException("userId"));
    }

    @GetMapping("/@self")
    @Operation(description = "Gets information about current User")
    public User getSelf(){
        LoginUser loginUser = LoginUser.getCurrent();
        return userService.getUser(loginUser.getUuid()).orElseThrow(() -> new RessourceNotFoundException("userId"));
    }

    @PutMapping("/@self")
    @Operation(description = "Updates information about current User")
    public User putSelf(@Validated @RequestBody @Parameter(description = "New User data", required = true) FormUser user) {
        User u = LoginUser.getCurrent().getUser(userService);
        BeanUtils.copyProperties(user, u);
        return userService.updateUser(u);
    }

    @GetMapping("/@self/guilds")
    @Operation(description = "Gets list of guilds the current User is a Member of")
    public List<Guild> getSelfGuilds(){
        User user = LoginUser.getCurrent().getUser(userService);
        return guildsService.getGuildsContaining(user);
    }

}
