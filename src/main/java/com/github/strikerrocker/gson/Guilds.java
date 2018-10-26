package com.github.strikerrocker.gson;


import java.util.ArrayList;
import java.util.Collection;

public class Guilds {
    private Collection<Guild> guilds = new ArrayList<>();
    private String USER_PREFIX = "!";
    private String ADMIN_PREFIX = "&";

    public Guilds addGuild(Guild guild) {
        guilds.add(guild);
        return this;
    }

    public Guilds removeGuild(Guild guild) {
        guilds.remove(guild);
        return this;
    }

    public Collection<Guild> getGuilds() {
        return guilds;
    }

    public String getAdminPrefix() {
        return ADMIN_PREFIX;
    }

    public Guilds setAdminPrefix(String ADMIN_PREFIX) {
        this.ADMIN_PREFIX = ADMIN_PREFIX;
        return this;
    }

    public String getUserPrefix() {
        return USER_PREFIX;
    }

    public Guilds setUserPrefix(String USER_PREFIX) {
        this.USER_PREFIX = USER_PREFIX;
        return this;
    }
}
