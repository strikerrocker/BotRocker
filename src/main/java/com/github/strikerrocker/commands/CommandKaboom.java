package com.github.strikerrocker.commands;


import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandKaboom extends Command {

    String url = "https://cdn.discordapp.com/attachments/439562187460313088/442723661871316992/Kaboom.jpg";

    public CommandKaboom() {
        super("kaboom", false);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        super.runCommand(event, args);
    }
}
