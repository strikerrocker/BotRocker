package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandUnpin extends Command {
    public CommandUnpin(CommandHandler handler) {
        super("unpin", true, handler);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (args.contains("all")) BotUtils.unpinMessage(event.getChannel(), true);
        else BotUtils.unpinMessage(event.getChannel(), false);
    }

    @Override
    public String getDesc() {
        return "Unpins the last pinned msg.";
    }
}