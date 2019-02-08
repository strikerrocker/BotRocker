package com.github.strikerrocker.commands;

import com.github.strikerrocker.utils.BotUtils;
import com.vdurmont.emoji.EmojiManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

public class CommandReact extends Command {
    public CommandReact() {
        super("react", false);
    }

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args) {
        args.forEach((alias) -> event.getGuild().getEmojis().forEach(emoji -> {
            IMessage toReact = BotUtils.getMsgBeforeGiven(event.getChannel());
            RequestBuffer.request(() -> {
                try {
                    if (alias.equals(emoji.getName())) {
                        toReact.addReaction(ReactionEmoji.of(emoji));
                    } else if (EmojiManager.isEmoji(alias))
                        toReact.addReaction(EmojiManager.getForAlias(alias));
                } catch (DiscordException e) {
                    e.printStackTrace();
                }
            });
        }));
    }

    @Override
    public String getDesc() {
        return "Reacts the last message with the given args as emoji key(Supports server emotes).";
    }
}