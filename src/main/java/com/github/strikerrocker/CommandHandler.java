package com.github.strikerrocker;

import com.github.strikerrocker.commands.*;
import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    public CommandHandler() {
        new CommandClear();
        new CommandPing();
        new CommandPrefix("prefix");
        new CommandPrefix("prefixad");
        new CommandReact();
        new CommandUnpin();
        new CommandList(true);
        new CommandList(false);
        new CommandCustomCreator();
        new CommandHelpMe();
    }

    @EventSubscriber
    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] argArray = event.getMessage().getContent().split(" ");
        if (argArray.length != 0) {
            List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
            argsList.remove(0);
            if (argArray[0].startsWith(BotUtils.USER_PREFIX)) {
                String prefix = argArray[0].substring(BotUtils.USER_PREFIX.length());
                if (MainRunner.INSTANCE.commands.get(prefix) != null)
                    MainRunner.INSTANCE.commands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The command `" + prefix + "` doesnt exists");
            } else if (argArray[0].startsWith(BotUtils.ADMIN_PREFIX) && PermissionUtils.hasPermissions(event.getGuild(), event.getAuthor(), Permissions.ADMINISTRATOR)) {
                String prefix = argArray[0].substring(BotUtils.ADMIN_PREFIX.length());
                if (MainRunner.INSTANCE.adminCommands.get(prefix) != null)
                    MainRunner.INSTANCE.adminCommands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The Admin command `" + prefix + "` doesnt exists");
            }
        }
    }
}