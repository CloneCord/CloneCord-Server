package net.leloubil.clonecordserver.data;


import lombok.Getter;

import java.util.UUID;

/**
 * This class represent a Role in a Guild
 * */
public class Role {

    @Getter
    private UUID uuid;

    @Getter
    private String name;

    @Getter
    private String displayName;

    @Getter
    private String hexColor;


}
