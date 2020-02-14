package net.leloubil.clonecordserver.controllers;

import net.leloubil.clonecordserver.data.Guild;
import net.leloubil.clonecordserver.formdata.LoginUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.services.GuildsService;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for endpoint related to user data
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    private final GuildsService guildsService;

    public UsersController(UserService userService, GuildsService guildsService) {
        this.userService = userService;
        this.guildsService = guildsService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId){
        return userService.getUser(userId).orElseThrow(() -> new RessourceNotFoundException("userId"));
    }

    @GetMapping("/@self")
    public User getSelf(){
        LoginUser loginUser = LoginUser.getCurrent();
        return userService.getUser(loginUser.getUuid()).orElseThrow(() -> new RessourceNotFoundException("userId"));
    }

    @PutMapping("/@self")
    public User putSelf(@Validated @RequestBody User user){
        LoginUser loginUser = LoginUser.getCurrent();
        user.setId(loginUser.getUuid());
        return userService.updateUser(user);
    }

    @GetMapping("/@self/guilds")
    public List<Guild> putSelf(){
        User user = LoginUser.getCurrent().getUser(userService);
        return guildsService.getGuildsContaining(user);
    }

}
