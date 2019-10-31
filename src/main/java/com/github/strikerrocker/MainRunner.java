package com.github.strikerrocker;

import com.github.strikerrocker.commands.Command;
import com.github.strikerrocker.commands.CommandPersistent;
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

import static com.github.strikerrocker.commands.CommandPrefix.DATA_PATH;

public class MainRunner {
    public static final File DATA_FOLDER = Paths.get("command_data").toFile();
    public static final Path CUSTOM_CMD_DATA_PATH = Paths.get("command_data/custom_commands.json");
    private static final Timer autoSaveTimer = new Timer();
    public static MainRunner INSTANCE = new MainRunner();

    static {
        DATA_FOLDER.mkdirs();
        try {
            DATA_PATH.toFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Map<String, Command> commands = new HashMap<>();
    public Map<String, Command> adminCommands = new HashMap<>();
    public Map<Long, Map<String, Map<String, String>>> customCommands = new HashMap<>();
    public Map<String, String> commandDescs = new HashMap<>();
    public Map<String, CommandPersistent> storageCommands = new HashMap<>();

    public static void main(String[] args) {
        IDiscordClient cli;
        cli = BotUtils.getBuiltDiscordClient(System.getenv("TOKEN"));
        cli.getDispatcher().registerListener(new CommandHandler());
        cli.login();
        GsonUtils.onStart();
        GsonUtils.read();
        Runtime.getRuntime().addShutdownHook(new Thread(GsonUtils::save));
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GsonUtils.save();
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(10));
    }
}