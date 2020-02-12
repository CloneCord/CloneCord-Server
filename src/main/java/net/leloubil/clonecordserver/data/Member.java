package net.leloubil.clonecordserver.data;

import lombok.Getter;

import java.util.List;
 /**
 * This class represent an user in a Guild
  * This isn't a {@link User}
 */

public class Member {

    @Getter
    private String name;

    @Getter
    private List<Role> roles;

}
