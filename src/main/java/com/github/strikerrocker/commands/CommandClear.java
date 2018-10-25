package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

public class CommandClear extends Command {

    public CommandClear() {
        super("clear", true);
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