package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandUnpin extends Command {
    public CommandUnpin() {
        super("unpin", true);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (args.contains("all")) BotUtils.unpinMessage(event.getChannel(), true);
        else BotUtils.unpinMessage(event.getChannel(), false);
    }

    @Override
    public String getDesc() {
        return "Unpins the last pinned msg(if has arg `all` will delete all)";
    }
}