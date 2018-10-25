package com.github.strikerrocker.commands;

import com.github.strikerrocker.MainRunner;
import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;
import java.util.Map;

public class CommandList extends Command {

    public CommandList(boolean admin) {
        super("cmds", admin);
    }

    public static String desc(Map<String, Command> commandMap, Map<String, String> commandDescMap) {
        String desc = "```";
        for (String key : commandMap.keySet()) {
            desc = desc.concat(key + "  :  " + commandDescMap.get(key) + "\n");
        }
        desc += "```";
        return desc;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        BotUtils.sendMessage(event.getChannel(), desc(this.admin ? MainRunner.INSTANCE.adminCommands : MainRunner.INSTANCE.commands, MainRunner.INSTANCE.commandDescs));
    }

    @Override
    public String getDesc() {
        return "Lists the commands.";
    }
}