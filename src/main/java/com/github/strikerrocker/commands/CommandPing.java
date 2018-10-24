package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandPing extends Command {
    public CommandPing(CommandHandler handler) {
        super("ping", false, handler);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        BotUtils.sendMessage(event.getChannel(), "pong!");
    }

    @Override
    public String getDesc() {
        return "Replays pong.";
    }
}