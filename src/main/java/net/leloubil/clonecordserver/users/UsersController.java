package net.leloubil.clonecordserver.users;

import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.exceptions.UserNotFoundException;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for endpoint related to user data
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String username){
        //todo get user data from database
        return null;
    }

    @GetMapping("/@self")
    public User getSelf(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUser(loginUser.getUuid()).orElseThrow(() -> new UserNotFoundException(loginUser.getUuid(),null));
    }

}
