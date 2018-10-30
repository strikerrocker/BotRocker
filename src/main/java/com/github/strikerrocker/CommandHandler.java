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

    CommandHandler() {
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
        //new CommandKaboom();
    }

    @EventSubscriber
    @SuppressWarnings("unused")
    public void onMessageReceived(MessageReceivedEvent event) {
        List<String> cmdParams = new ArrayList<>(Arrays.asList(event.getMessage().getContent().split(" ")));
        if (cmdParams.size() != 0) {
            List<String> argsList = new ArrayList<>(cmdParams);
            argsList.remove(0);
            String command = cmdParams.get(0).toLowerCase();
            if (command.startsWith(BotUtils.ADMIN_PREFIX) && PermissionUtils.hasPermissions(event.getGuild(), event.getAuthor(), Permissions.ADMINISTRATOR)) {
                String prefix = command.substring(BotUtils.ADMIN_PREFIX.length());
                if (MainRunner.INSTANCE.adminCommands.get(prefix) != null)
                    MainRunner.INSTANCE.adminCommands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The Admin command `" + prefix + "` doesn't exists");
            } else if (command.startsWith(BotUtils.USER_PREFIX)) {
                String prefix = command.substring(BotUtils.USER_PREFIX.length());
                if (MainRunner.INSTANCE.commands.get(prefix) != null)
                    MainRunner.INSTANCE.commands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The command `" + prefix + "` doesn't exists");
            }
        }
    }
}