package com.github.strikerrocker;

import com.github.strikerrocker.gson.GsonUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.PermissionUtils;

import java.util.*;

public class CommandHandler {

    private static Map<String, Command> commands = new HashMap<>();
    private static Map<String, Command> adminCommands = new HashMap<>();
    private static Map<String, String> commandDescs = new HashMap<>();

    static {
        commandDescs.put("ping", "Replays pong.");
        commandDescs.put("react", "Reacts the message with the given args as emoji key.");
        commandDescs.put("cmds", "Lists the commands.");
        commandDescs.put("pre", "Sets the user prefix");
        commandDescs.put("pread", "Sets the admin prefix");
        commandDescs.put("clear", "Clears the messages in the channel.");
        commandDescs.put("unpin", "Unpins the last pinned msg.");
        adminCommands.put("unpin", ((event, args) -> {
            if (args.contains("all")) {
                BotUtils.unpinMessage(event.getChannel(), event.getChannel().getPinnedMessages().get(0));
            } else {
                BotUtils.unpinMessage(event.getChannel(), null);
            }
        }));
        adminCommands.put("clear", ((event, args) -> BotUtils.clear(event.getChannel())));
        adminCommands.put("cmds", ((event, args) -> BotUtils.sendMessage(event.getChannel(), BotUtils.desc(adminCommands, commandDescs))));
        adminCommands.put("pread", (event, args) -> {
            BotUtils.ADMIN_PREFIX = args.get(0);
            GsonUtils.save();
            BotUtils.sendMessage(event.getChannel(), "The admin Prefix is " + BotUtils.ADMIN_PREFIX);
        });
        adminCommands.put("pre", (event, args) -> {
            BotUtils.USER_PREFIX = args.get(0);
            GsonUtils.save();
            BotUtils.sendMessage(event.getChannel(), "The User Prefix is " + BotUtils.USER_PREFIX);
        });
        commands.put("cmds", (event, args) -> BotUtils.sendMessage(event.getChannel(), BotUtils.desc(commands, commandDescs)));
        commands.put("react", ((event, args) -> {
            for (String alias : args) {
                BotUtils.react(event.getMessage(), alias);
            }
        }));
        commands.put("ping", (event, args) -> BotUtils.sendMessage(event.getChannel(), "pong"));
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
                if (commands.get(prefix) != null) commands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The command `" + prefix + "` doesnt exists");
            } else if (argArray[0].startsWith(BotUtils.ADMIN_PREFIX) && PermissionUtils.hasPermissions(event.getGuild(), event.getAuthor(), Permissions.ADMINISTRATOR)) {
                String prefix = argArray[0].substring(BotUtils.ADMIN_PREFIX.length());
                if (adminCommands.get(prefix) != null) adminCommands.get(prefix).runCommand(event, argsList);
                else BotUtils.sendMessage(event.getChannel(), "The Admin command `" + prefix + "` doesnt exists");
            }
        }
    }
}