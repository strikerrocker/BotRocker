package com.github.strikerrocker.commands;

import com.github.strikerrocker.MainRunner;
import com.github.strikerrocker.gson.GsonUtils;
import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Collections;
import java.util.List;

//TODO try to convert this command to use CommandPersistent
public class CommandCustom extends Command {
    String reply;
    String desc;
    long guild;

    public CommandCustom(String cmd, String reply, String desc, long guild) {
        super(cmd, false);
        this.reply = reply;
        this.desc = desc;
        this.guild = guild;
        MainRunner.INSTANCE.customCommands.put(guild, Collections.singletonMap(cmd, Collections.singletonMap(reply, desc)));
        GsonUtils.saveCustomCommandData();
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (event.getGuild().getLongID() == guild) BotUtils.sendMessage(event.getChannel(), reply);
    }

    @Override
    public String getDesc() {
        return desc != null ? desc : super.getDesc();
    }

    public void delete(IChannel channel) {
        if (MainRunner.INSTANCE.commands.remove(cmd) == null) {
            BotUtils.sendMessage(channel, "No custom command with alias `" + cmd + "` exists");
        } else {
            BotUtils.sendMessage(channel, "The command with alias `" + cmd + "` has been deleted successfully");
            MainRunner.INSTANCE.customCommands.remove(guild, Collections.singletonMap(cmd, Collections.singletonMap(reply, desc)));
            MainRunner.INSTANCE.commandDescs.remove(cmd);
        }
        GsonUtils.saveCustomCommandData();
    }
}
