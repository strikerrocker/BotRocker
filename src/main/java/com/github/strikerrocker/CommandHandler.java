package com.github.strikerrocker;

import com.github.strikerrocker.commands.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.PermissionUtils;

import java.util.*;

public class CommandHandler {

    public Map<String, Command> commands = new HashMap<>();
    public Map<String, Command> adminCommands = new HashMap<>();
    public Map<String, String> commandDescs = new HashMap<>();

    public CommandHandler() {
        new CommandClear(this);
        new CommandPing(this);
        new CommandPrefix("prefix", this);
        new CommandPrefix("prefixad", this);
        new CommandReact(this);
        new CommandUnpin(this);
        new CommandList(true, this);
        new CommandList(false, this);
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