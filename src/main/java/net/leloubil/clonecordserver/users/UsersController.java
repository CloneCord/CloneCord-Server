package net.leloubil.clonecordserver.users;

import net.leloubil.clonecordserver.data.LoginUser;
import net.leloubil.clonecordserver.data.User;
import net.leloubil.clonecordserver.exceptions.UserNotFoundException;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
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
        return userService.getUserByName(username).orElseThrow(() -> new UserNotFoundException(null,username));
    }

    @GetMapping("/@self")
    public User getSelf(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUser(loginUser.getUuid()).orElseThrow(() -> new UserNotFoundException(loginUser.getUuid(),null));
    }

    @PutMapping("/@self")
    public User putSelf(@Validated @RequestBody User user){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setId(loginUser.getUuid());
        return userService.updateUser(user);
    }

}
