package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandReact extends Command {
    public CommandReact(CommandHandler handler) {
        super("react", false, handler);
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