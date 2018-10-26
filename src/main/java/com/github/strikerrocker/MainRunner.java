package com.github.strikerrocker;

import com.github.strikerrocker.commands.Command;
import com.github.strikerrocker.gson.GsonUtils;
import com.github.strikerrocker.utils.BotUtils;
import sx.blah.discord.api.IDiscordClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainRunner {
    public static final File DATA_FOLDER = Paths.get("command_data").toFile();
    public static final Path DATA_PATH = Paths.get("command_data/commands_data.json");
    public static final Path CUSTOM_DATA_PATH = Paths.get("command_data/custom_commands.json");
    private static final Timer autoSaveTimer = new Timer();
    public static IDiscordClient cli;
    public static MainRunner INSTANCE = new MainRunner();

    static {
        try {
            DATA_FOLDER.mkdirs();
            if (DATA_PATH.toFile().createNewFile())
                GsonUtils.saveCommandData();
            if (CUSTOM_DATA_PATH.toFile().createNewFile())
                GsonUtils.saveCustomCommandData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Command> commands = new HashMap<>();
    public Map<String, Command> adminCommands = new HashMap<>();
    public Map<Long, Map<String, Map<String, String>>> customCommands = new HashMap<>();
    public Map<String, String> commandDescs = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }
        GsonUtils.readAll();
        cli = BotUtils.getBuiltDiscordClient(args[0]);
        cli.getDispatcher().registerListener(new CommandHandler());
        cli.login();
        Runtime.getRuntime().addShutdownHook(new Thread(GsonUtils::saveAll));
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GsonUtils.saveAll();
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(10));
    }
}