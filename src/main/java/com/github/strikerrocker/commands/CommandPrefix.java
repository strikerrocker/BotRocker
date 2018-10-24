package com.github.strikerrocker.commands;

import com.github.strikerrocker.BotUtils;
import com.github.strikerrocker.Command;
import com.github.strikerrocker.CommandHandler;
import com.github.strikerrocker.gson.GsonUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public class CommandPrefix extends Command {
    boolean adminPrefix;

    public CommandPrefix(String cmd, CommandHandler handler) {
        super(cmd, true, handler);
        adminPrefix = !cmd.equals("prefix");
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (!args.get(0).equals("show")) {
            if (!adminPrefix) {
                BotUtils.USER_PREFIX = args.get(0);
                BotUtils.sendMessage(event.getChannel(), "The User Prefix has been set to " + BotUtils.USER_PREFIX);
            } else {
                BotUtils.ADMIN_PREFIX = args.get(0);
                BotUtils.sendMessage(event.getChannel(), "The Admin Prefix has been set to " + BotUtils.ADMIN_PREFIX);
            }
            GsonUtils.save();
        } else {
            String msg = "";
            msg = this.cmd.equals("prefix") ? "The User Prefix is " + BotUtils.USER_PREFIX : "The Admin Prefix is " + BotUtils.ADMIN_PREFIX;
            BotUtils.sendMessage(event.getChannel(), msg);
        }
    }

    @Override
    public String getDesc() {
        if (!adminPrefix)
            return "Sets the user prefix";
        else return "Sets the admin prefix";
    }
}