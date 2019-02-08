package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.strikerrocker.gson.GsonUtils.gson;

public class CommandPrefix extends CommandPersistent {
    public static final Path DATA_PATH = Paths.get("command_data/prefix.json");
    private static final Type type = new TypeToken<Map<String, String>>() {
    }.getType();
    private boolean adminPrefix;

    public CommandPrefix(String cmd) {
        super(cmd, true);
        adminPrefix = !cmd.equals("prefix");
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        if (args.size() > 0 && !args.get(0).equals("show")) {
            if (!adminPrefix) {
                BotUtils.USER_PREFIX = args.get(0);
                BotUtils.sendMessage(event.getChannel(), "The User Prefix has been set to " + BotUtils.USER_PREFIX);
            } else {
                BotUtils.ADMIN_PREFIX = args.get(0);
                BotUtils.sendMessage(event.getChannel(), "The Admin Prefix has been set to " + BotUtils.ADMIN_PREFIX);
            }
            this.save();
        } else {
            String msg = this.cmd.equals("prefix") ? "The User Prefix is " + BotUtils.USER_PREFIX : "The Admin Prefix is " + BotUtils.ADMIN_PREFIX;
            BotUtils.sendMessage(event.getChannel(), msg);
        }
    }

    @Override
    public String getDesc() {
        if (!adminPrefix)
            return "Sets the user prefix(if args has show it will send the prefix)";
        else return "Sets the admin prefix(if args has show it will send the prefix)";
    }

    @Override
    public void read() {
        try {
            if (adminPrefix) {
                Map<String, String> namePrefixMap = gson.fromJson(FileUtils.readFileToString(DATA_PATH.toFile(), Charset.defaultCharset()), type);
                if (namePrefixMap == null) namePrefixMap = new HashMap<>();
                BotUtils.USER_PREFIX = namePrefixMap.getOrDefault("user_prefix", "!");
                System.out.println("User Prefix has been set to " + BotUtils.USER_PREFIX);
                BotUtils.ADMIN_PREFIX = namePrefixMap.getOrDefault("admin_prefix", "&");
                System.out.println("Admin Prefix has been set to " + BotUtils.ADMIN_PREFIX);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            Files.deleteIfExists(DATA_PATH);
            PrintWriter printWriter = new PrintWriter(new FileWriter(DATA_PATH.toFile()));
            Map<String, String> namePrefixMap = new HashMap<>();
            namePrefixMap.put("user_prefix", "!");
            namePrefixMap.put("admin_prefix", "&");
            System.out.println(namePrefixMap.toString());
            printWriter.print(gson.toJson(namePrefixMap, type));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        try {
            if (DATA_PATH.toFile().createNewFile())
                this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}