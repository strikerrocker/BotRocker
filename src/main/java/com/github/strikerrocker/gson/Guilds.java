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

    public String getADMIN_PREFIX() {
        return ADMIN_PREFIX;
    }

    public Guilds setADMIN_PREFIX(String ADMIN_PREFIX) {
        this.ADMIN_PREFIX = ADMIN_PREFIX;
        return this;
    }

    public String getUSER_PREFIX() {
        return USER_PREFIX;
    }

    public Guilds setUSER_PREFIX(String USER_PREFIX) {
        this.USER_PREFIX = USER_PREFIX;
        return this;
    }
}
