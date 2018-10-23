package com.github.strikerrocker;

import com.vdurmont.emoji.EmojiManager;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Map;

public class BotUtils {
    public static String USER_PREFIX = "!";
    public static String ADMIN_PREFIX = "&";

    static IDiscordClient getBuiltDiscordClient(String token) {
        return new ClientBuilder()
                .withToken(token)
                .withRecommendedShardCount()
                .setPresence(StatusType.ONLINE, ActivityType.WATCHING, "Striker")
                .build();
    }

    static void sendMessage(IChannel channel, String message) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void pinMessage(IChannel channel, IMessage message) {
        RequestBuffer.request(() -> {
            try {
                channel.pin(message);
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void unpinMessage(IChannel channel, IMessage message) {
        RequestBuffer.request(() -> {
            try {
                if (!channel.getPinnedMessages().isEmpty()) {
                    if (message != null) {
                        channel.unpin(message);
                    } else {
                        for (IMessage msg : channel.getPinnedMessages()) {
                            channel.unpin(msg);
                        }
                    }
                } else
                    sendMessage(channel, "No pinned msg exists");
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void clear(IChannel channel) {
        RequestBuffer.request(() -> {
            try {
                channel.getFullMessageHistory().bulkDelete();
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void react(IMessage message, String alias) {
        RequestBuffer.request(() -> {
            try {
                message.addReaction(EmojiManager.getForAlias(alias));
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static String desc(Map<String, Command> commandMap, Map<String, String> commandDescMap) {
        String desc = "```";
        for (String key : commandMap.keySet()) {
            desc = desc.concat(key + "  :  " + commandDescMap.get(key) + "\n");
        }
        desc += "```";
        return desc;
    }
}