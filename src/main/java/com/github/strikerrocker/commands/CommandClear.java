package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

public class CommandClear extends Command {

    public CommandClear(CommandHandler handler) {
        super("clear", true, handler);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        RequestBuffer.request(() -> {
            try {
                IChannel channel = event.getChannel();
                int size = channel.getFullMessageHistory().size();
                channel.getFullMessageHistory().bulkDelete();
                if (!channel.getFullMessageHistory().isEmpty()) this.runCommand(event, args);
                BotUtils.sendMessage(event.getChannel(), size + " messages have been deleted");
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getDesc() {
        return "Clears the messages in the channel.";
    }
}