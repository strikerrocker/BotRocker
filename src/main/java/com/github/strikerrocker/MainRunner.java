package com.github.strikerrocker;

import com.github.strikerrocker.gson.GsonUtils;
import sx.blah.discord.api.IDiscordClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainRunner {
    public static final File DATA_FOLDER = Paths.get("command_data").toFile();
    public static final Path FILE_PATH = Paths.get("command_data/commands_data.json");
    private static final Timer autoSaveTimer = new Timer();
    public static IDiscordClient cli;
    public static CommandHandler INSTANCE = new CommandHandler();

    static {
        DATA_FOLDER.mkdirs();
        try {
            if (FILE_PATH.toFile().createNewFile())
                GsonUtils.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }
        GsonUtils.read(FILE_PATH.toFile());
        cli = BotUtils.getBuiltDiscordClient(args[0]);
        cli.getDispatcher().registerListener(INSTANCE);
        cli.login();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GsonUtils.save();
        }));
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GsonUtils.save();
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(10));
    }
}