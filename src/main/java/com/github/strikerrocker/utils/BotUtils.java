package com.github.strikerrocker.utils;

import com.vdurmont.emoji.Emoji;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class BotUtils {
    public static String USER_PREFIX = "!";
    public static String ADMIN_PREFIX = "&";

    public static IDiscordClient getBuiltDiscordClient(String token) {
        return new ClientBuilder()
                .withToken(token)
                .withRecommendedShardCount()
                .setPresence(StatusType.ONLINE, ActivityType.WATCHING, "Striker")
                .build();
    }

    public static void sendMessage(IChannel channel, String message) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    public static void sendMessage(IChannel channel, String message, long delay, long period) {
        RequestBuffer.request(() -> {
            try {
                IMessage msg = channel.sendMessage(message);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                                              @Override
                                              public void run() {
                                                  ArrayList<IMessage> messages = new ArrayList<>();
                                                  messages.add(msg);
                                                  channel.bulkDelete(messages);
                                                  timer.cancel();
                                              }
                                          }
                        , TimeUnit.SECONDS.toMillis(delay), TimeUnit.SECONDS.toMillis(period));
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    public static void pinMessage(IChannel channel, IMessage message) {
        RequestBuffer.request(() -> {
            try {
                channel.pin(message);
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });
    }

    public static void unpinMessage(IChannel channel, boolean first) {
        RequestBuffer.request(() -> {
            try {
                if (!channel.getPinnedMessages().isEmpty()) {
                    if (first) {
                        channel.unpin(channel.getPinnedMessages().get(0));
                    } else {
                        for (IMessage msg : channel.getPinnedMessages()) {
                            channel.unpin(msg);
                        }
                    }
                } else
                    sendMessage(channel, "No pinned msg exists");
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });
    }

    public static void react(IMessage message, Emoji alias) {
        RequestBuffer.request(() -> {
            try {
                message.addReaction(alias);
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });
    }

    public static void react(IMessage message, IEmoji emoji) {
        RequestBuffer.request(() -> {
            try {
                message.addReaction(ReactionEmoji.of(emoji));
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });
    }

    public static IMessage getMsgBeforeGiven(IChannel channel) {
        return channel.getMessageHistory(2).get(1);
    }
}