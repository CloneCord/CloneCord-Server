package net.leloubil.clonecordserver.data;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class Guild {

    @Getter
    private UUID uuid;

    @Getter
    private String name;

    @Getter
    private String displayName;

    @Getter
    private List<Member> members;

}
