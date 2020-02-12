package net.leloubil.clonecordserver.data;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * This class represent an User on the infrastructure
 * An user is someone that uses the services, an Account.
 * */
public class User {

    @Getter
    private String name;

    @Getter
    private String displayName;

    @Getter
    private UUID uuid;

    @Getter
    private BufferedImage avatar;

}
