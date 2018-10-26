package com.github.strikerrocker.commands;

import com.github.strikerrocker.MainRunner;
import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandCustomCreator extends Command {
    public CommandCustomCreator() {
        super("custom", false);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        String arg1 = args.size() > 0 ? args.get(0) : "";
        String name = args.size() > 1 ? args.get(1) : "";
        String reply = args.size() > 2 ? args.get(2) : "";
        String desc = args.size() > 3 ? !args.get(3).equals("") ? args.get(3) : "" : "";
        if (arg1.equals("create")) {
            if (!name.equals("") && !reply.equals("")) {
                new CommandCustom(name, reply, desc, event.getGuild().getLongID());
                BotUtils.sendMessage(event.getChannel(), "New Custom Command has been created with alias `" + name + "` and it replies `" + reply + "`");
            }
        } else if (arg1.equals("delete") && !name.equals("")) {
            if (MainRunner.INSTANCE.commands.get(name) instanceof CommandCustom)
                ((CommandCustom) MainRunner.INSTANCE.commands.get(name)).delete(event.getChannel());
        }
    }
}