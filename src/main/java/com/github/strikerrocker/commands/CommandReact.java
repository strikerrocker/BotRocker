package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandReact extends Command {
    public CommandReact() {
        super("react", false);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        for (String alias : args) {
            BotUtils.react(event.getMessage(), alias);
        }
    }

    @Override
    public String getDesc() {
        return "Reacts the message with the given args as emoji key.";
    }
}