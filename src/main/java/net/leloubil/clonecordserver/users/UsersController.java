package net.leloubil.clonecordserver.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/user/{userName}")
    public String getUser(@PathVariable("userName") String username){
        //todo get user data from database
        return username;
    }

}
