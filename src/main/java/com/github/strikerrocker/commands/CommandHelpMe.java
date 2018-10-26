package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandHelpMe extends Command {
    public CommandHelpMe() {
        super("helpme", false);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        String output = "Help is on the way!";
        output += "\n http://lmgtfy.com/?q=";
        for (String string : args) {
            output += string;
            if (!(args.indexOf(string) > args.size())) {
                output += "+";
            }
        }
        BotUtils.sendMessage(event.getChannel(), output);
    }

    @Override
    public String getDesc() {
        return "Returns a lmgtfy link";
    }
}
