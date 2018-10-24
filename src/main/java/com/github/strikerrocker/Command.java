package com.github.strikerrocker;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class Command {
    public boolean admin = false;
    public String cmd = "";


    public Command(String cmd, boolean admin, CommandHandler handler) {
        this.admin = admin;
        if (admin) {
            handler.adminCommands.put(cmd, this);
        } else handler.commands.put(cmd, this);
        handler.commandDescs.put(cmd, getDesc());
        this.cmd = cmd;
    }

    public void runCommand(MessageReceivedEvent event, List<String> args) {
    }

    public String getDesc() {
        return "";
    }
}