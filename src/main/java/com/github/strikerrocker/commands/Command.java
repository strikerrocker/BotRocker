package com.github.strikerrocker.commands;

import com.github.strikerrocker.MainRunner;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class Command {
    public boolean admin;
    public String cmd;

    public Command(String cmd, boolean admin) {
        this.admin = admin;
        if (admin) MainRunner.INSTANCE.adminCommands.put(cmd, this);
        else MainRunner.INSTANCE.commands.put(cmd, this);
        MainRunner.INSTANCE.commandDescs.put(cmd, getDesc());
        this.cmd = cmd;

    }

    public void runCommand(MessageReceivedEvent event, List<String> args) {
    }

    public String getDesc() {
        return "";
    }
}