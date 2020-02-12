package net.leloubil.clonecordserver.users;

import org.springframework.web.bind.annotation.*;

/**
 * Controller for endpoint related to user data
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/{userName}")
    public String getUser(@PathVariable("userName") String username){
        //todo get user data from database
        return username;
    }

}
