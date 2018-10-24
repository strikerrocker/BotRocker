package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandList extends Command {
    CommandHandler commandHandler;

    public CommandList(boolean admin, CommandHandler handler) {
        super("cmds", admin, handler);
        commandHandler = handler;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        BotUtils.sendMessage(event.getChannel(), BotUtils.desc(this.admin ? commandHandler.adminCommands : commandHandler.commands, commandHandler.commandDescs));
    }

    @Override
    public String getDesc() {
        return "Lists the commands.";
    }
}